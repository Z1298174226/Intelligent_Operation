package com.lex.zhao.textKeyword.bussiness;


import com.lex.zhao.textKeyword.topo.WeightedGraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qtfs on 2018/6/10.
 */
public class BusinessTools {

    public Random rand = new Random();
    public Lock lock = new ReentrantLock();
    public Condition empty = lock.newCondition();
    public  Queue<Business> list = new LinkedList<Business>();
    public int count = 0;

    //业务生成函数，随机生成业务占用带宽以及业务持续时间
    public  Business business_generation(WeightedGraph graph) {
        while(true) {
            int src = rand.nextInt(graph.getVertexs());
            int dst = rand.nextInt(graph.getVertexs());
            while(src == dst) {
                dst = rand.nextInt(graph.getVertexs());
            }
            int bandwidth = rand.nextInt(10) + 1;
            long holdTime = rand.nextInt(3000) + 2000;
            lock.lock();
            try{
                Business business = new Business(++count, src, dst, bandwidth, holdTime);
                list.add(business);
                empty.signal();
            }finally {
                lock.unlock();
            }
            //业务发生间隔
            try{
                TimeUnit.MILLISECONDS.sleep(getPossionVariable(4) * 50);
            }catch(InterruptedException ex) {
            }
        }
    }

    //业务生成函数，随机生成业务占用带宽以及业务持续时间
    public  Business business_generation_DBA(WeightedGraph graph, Type type) {
        while(true) {
            int src = rand.nextInt(graph.getVertexs());
            int dst = rand.nextInt(graph.getVertexs());
            while(src == dst) {
                dst = rand.nextInt(graph.getVertexs());
            }
            int flow = rand.nextInt(20) + 10;
            lock.lock();
            try{
                Business business = new Business(++count, src, dst, flow, type);
                list.add(business);
                empty.signal();
            }finally {
                lock.unlock();
            }
            //业务发生间隔，服从泊松分布
            try{
                TimeUnit.MILLISECONDS.sleep(getPossionVariable(4) * 100);
            }catch(InterruptedException ex) {
            }
        }
    }

    //业务到来服从泊松分布
    // 生成服从泊松分布的随机数
    private static int getPossionVariable(double lamda) {
        int x = 0;
        double y = Math.random(), cdf = getPossionProbability(x, lamda);
        while (cdf < y) {
            x++;
            cdf += getPossionProbability(x, lamda);
        }
        return x;
    }

    private static double getPossionProbability(int k, double lamda) {
        double c = Math.exp(-lamda), sum = 1;
        for (int i = 1; i <= k; i++) {
            sum *= lamda / i;
        }
        return sum * c;
    }
}
