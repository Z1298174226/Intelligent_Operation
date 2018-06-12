package com.lex.zhao.textKeyword;

import com.lex.zhao.textKeyword.bussiness.Business;
import com.lex.zhao.textKeyword.bussiness.BusinessTools;
import com.lex.zhao.textKeyword.loadbalance.LoadBalanceTools;
import com.lex.zhao.textKeyword.loadbalance.RoundRobin;
import com.lex.zhao.textKeyword.topo.Edge;
import com.lex.zhao.textKeyword.topo.WeightedGraph;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by qtfs on 2018/6/10.
 */

public class Scene {
    private int threshold;
    public Scene(int threshold) {
        this.threshold = threshold;
    }
    public void run() {
        //拓扑生成
        final WeightedGraph graph = new WeightedGraph("src\\main\\java\\com\\lex\\zhao\\textKeyword\\topo\\topo_10.txt");
        //创建线程池
        final ExecutorService executor = Executors.newCachedThreadPool();
        //生成业务的工具类
        final BusinessTools businessTools = new BusinessTools();
        //基于AQS的Lock对象
        final Lock bandwidth_lock = new ReentrantLock();
        final Condition utilization = bandwidth_lock.newCondition();
       //业务生成线程
        Runnable gererate_business = new Runnable() {
            @Override
            public void run() {
                businessTools.business_generation(graph);
            }
        };
        //带宽计算线程，业务到来时，对应链路带宽减少业务需求量，业务离去时，对应链路带宽恢复
        Runnable bandwidth_compute = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    businessTools.lock.lock();
                    try {
                        //业务列表为空，此时等待新业务的到来
                        if (businessTools.list.isEmpty())
                            try {
                                businessTools.empty.await();
                            }catch(InterruptedException ex){}

                    }finally {
                        businessTools.lock.unlock();
                    }
                    final Business business = businessTools.list.poll();
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
//                            System.out.println(business);
                            List<Edge> path = LoadBalanceTools.buildPath(graph, business.getSrc(), business.getDst(), threshold);
//                            System.out.println(path);
                            bandwidth_lock.lock();
                            try {
                                //是否已经没有可以选择的链路，实在没有则业务阻塞
//                                if(!RoundRobin.roundRobin(graph, threshold))
                                if(path.size() == 0)
                                    try {
                                        System.out.println("The usageRate of network is more than " + threshold + "%, " + " business " + "[" + business.getId() + "]" + " is blocked! Waiting.......");
                                        utilization.await();
                                        System.out.println("business" + "[" + business.getId() + "] " + " go on........");
                                } catch(InterruptedException ex){}
                            }finally {
                                bandwidth_lock.unlock();
                            }
                            synchronized (graph) {
                                for (Edge e : path)
                                    e.setBandwidth(e.getBandwidth() - business.getBandwidth());
                            }
                            //休眠来模拟业务持续时间
                            try {
                                TimeUnit.MILLISECONDS.sleep(business.getPersistTime());
                            } catch (InterruptedException ex) {
                            }
                            synchronized (graph) {
                                for (Edge e : path)
                                    e.setBandwidth(e.getBandwidth() + business.getBandwidth());
                            }
                            bandwidth_lock.lock();
                            try {
                                //轮训链路是否超过阈值，若没有，通知被阻塞线程可以继续进行
                                if(RoundRobin.roundRobin(graph, threshold)) {
                                    utilization.signal();
                                }
                            }finally {
                                bandwidth_lock.unlock();
                            }
                            //业务完成
//                            System.out.println("The business completed, id is " + business.getId());
                        }
                    });
                }

            }
        };
        executor.submit(gererate_business);
        executor.submit(bandwidth_compute);
    }
}
