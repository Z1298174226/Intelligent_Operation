package com.lex.zhao.textKeyword.loadbalance;

import com.lex.zhao.textKeyword.topo.Edge;
import com.lex.zhao.textKeyword.topo.WeightedGraph;

/**
 * Created by qtfs on 2018/6/10.
 */
public class RoundRobin {

    //轮训链路带宽利用率是否超过阈值
    public static boolean roundRobin(WeightedGraph graph, int threshold) {
        for(Edge e : graph.adj(0)) {
            if(e.getBandwidth() < 0 || e.usageRate() * 100 > threshold ) {
                return false;
            }
        }
        return true;
    }


}
