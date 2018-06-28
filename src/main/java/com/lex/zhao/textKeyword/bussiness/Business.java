package com.lex.zhao.textKeyword.bussiness;

/**
 * Created by qtfs on 2018/6/10.
 */

import com.lex.zhao.textKeyword.networkconf.ProtocolToPriority;

import java.util.Map;

public class Business {
    //业务id
    private int id;
    //源节点
    private int src;
    //宿节点
    private int dst;
    //所需带宽
    private int bandwidth;
    //业务总流量
    private int flow;
    //业务持续时间
    private long holdTime;
    //业务优先级
    private int priority;
    //业务类型
    private Type type;


    public Business(int id, int src, int dst, int bandwidth, long persistTime) {
        this.id = id;
        this.src = src;
        this.dst = dst;
        this.bandwidth = bandwidth;
        this.holdTime = persistTime;
    }

    public Business(int id, int src, int dst, int flow, Type type) {
        this.id = id;
        this.src = src;
        this.dst = dst;
        this.flow = flow;
        this.type = type;
        this.priority = ProtocolToPriority.protocolMap.get(type);
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
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

    public Type getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public int getFlow() {
        return flow;
    }

    @Override
    public String toString() {
        String s = String.format("id : [%d], src : [%d], dst : [%d], bandwidth : [%2dM/s], holdTime : [%dms]" , id, src, dst, bandwidth, getPersistTime());
        return s;
    }

    public String businessPrint() {
        String s = String.format("id : [%d], src : [%d], dst : [%d], flow : [%2dM], priority : [%d]" , id, src, dst, flow, priority);
        return s;
    }

}
