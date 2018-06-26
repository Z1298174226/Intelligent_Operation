package com.lex.zhao.textKeyword.BusinessIdentification;

import com.lex.zhao.textKeyword.bussiness.Business;
import com.lex.zhao.textKeyword.bussiness.BusinessTools;
import com.lex.zhao.textKeyword.bussiness.Type;
import com.lex.zhao.textKeyword.loadbalance.LoadBalanceTools;
import com.lex.zhao.textKeyword.loadbalance.RoundRobin;
import com.lex.zhao.textKeyword.networkconf.PriorityToBandWidth;
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
 * Created by qtfs on 2018/6/25.
 */
public class Scene_BusinessIdentification {
    private int threshold;
    public Scene_BusinessIdentification(int threthold) {
        this.threshold = threthold;
    }
    public void run(final Type type) {
        //拓扑读取
        final WeightedGraph graph = new WeightedGraph("src\\main\\java\\com\\lex\\zhao\\textKeyword\\topo\\topo.txt");
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
                businessTools.business_generation_DBA(graph, type);
            }
        };
        //带宽计算线程，全网不足以为高价值业务分配带宽时，减少低价值业务带宽分配
        Runnable bandwidth_compute = new Runnable() {
            @Override
            public void run() {
                businessTools.lock.lock();
                try {
                    //业务列表为空，此时等待新业务的到来
                    if (businessTools.list.isEmpty())
                        try {
                        //休眠，等待新业务的到来
                            businessTools.empty.await();
                        }catch(InterruptedException ex){}
                }finally {
                    businessTools.lock.unlock();
                }
                final Business business = businessTools.list.poll();
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(business.businessPrint());
                        List<Edge> path = LoadBalanceTools.buildPath_priority(graph, business.getSrc(), business.getDst(), threshold, business.getPriority());
                        System.out.println(path);
                        bandwidth_lock.lock();
                        try {
                            //是否已经没有可以选择的链路，实在没有则业务阻塞
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
                                e.setBandwidth(e.getBandwidth() - PriorityToBandWidth.bandWidthMap.get(business.getPriority()));
                        }
                        //休眠来模拟业务持续时间
                        try {
                            TimeUnit.SECONDS.sleep((business.getFlow() / PriorityToBandWidth.bandWidthMap.get(business.getPriority())));
                        } catch (InterruptedException ex) {
                        }
                        synchronized (graph) {
                            for (Edge e : path)
                                e.setBandwidth(e.getBandwidth() + PriorityToBandWidth.bandWidthMap.get(business.getPriority()));
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
                    }
                });
            }
        };
        executor.submit(gererate_business);
        while(true) {
            executor.submit(bandwidth_compute);
        }
    }
}
