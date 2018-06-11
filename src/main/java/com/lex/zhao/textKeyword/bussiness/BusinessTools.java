package com.lex.zhao.textKeyword.bussiness;


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

    //业务生成函数
    public  Business business_generation() {
        while(true) {
            int src = rand.nextInt(7);
            int dst = rand.nextInt(7);
            while(src == dst) {
                dst = rand.nextInt(7);
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
