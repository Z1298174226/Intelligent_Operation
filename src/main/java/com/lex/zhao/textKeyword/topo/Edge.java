package com.lex.zhao.textKeyword.topo;

/**
 * Created by qtfs on 2018/6/5.
 * 链路
 */


public class Edge {
    private int node_ip_one;
    private int node_ip_other;
    private int bandwidth;
    private int capacity;
    public int from;

    public Edge(int node_ip_one, int node_ip_other, int capacity) {
        this.node_ip_one = node_ip_one;
      //  this.from = node_ip_one;
        this.node_ip_other = node_ip_other;
        this.bandwidth = capacity;
        this.capacity = capacity;
    }

    public int get_one_node() {
        return node_ip_one;
    }

    public int get_other_node() {
        return node_ip_other;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setBandwidth(int bandwidth) {
       this.bandwidth = bandwidth;
    }

    public double usageRate() {
        return (capacity - bandwidth) / (double) capacity;
    }


    @Override
    public String toString() {
        return "link " + node_ip_one + " to " + node_ip_other + ", bandwidth is " + bandwidth;
    }

}
