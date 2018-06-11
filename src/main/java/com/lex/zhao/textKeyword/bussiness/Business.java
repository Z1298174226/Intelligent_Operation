package com.lex.zhao.textKeyword.bussiness;

/**
 * Created by qtfs on 2018/6/10.
 */
public class Business {
    //业务id
    private int id;
    //源节点
    private int src;
    //宿节点
    private int dst;
    //所需带宽
    private int bandwidth;
    //业务持续时间
    private long holdTime;

    public Business(int id, int src, int dst, int bandwidth, long persistTime) {
        this.id = id;
        this.src = src;
        this.dst = dst;
        this.bandwidth = bandwidth;
        this.holdTime = persistTime;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public long getPersistTime() {
        return holdTime;
    }

    public int getSrc() {
        return src;
    }

    public int getDst() {
        return dst;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String s = String.format("id : [%d], src : [%d], dst : [%d], bandwidth : [%2d], holdTime : [%d]" , id, src, dst, bandwidth, getPersistTime());
        return s;
    }
}
