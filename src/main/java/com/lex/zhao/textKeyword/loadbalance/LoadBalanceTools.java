package com.lex.zhao.textKeyword.loadbalance;


import com.lex.zhao.textKeyword.topo.Edge;
import com.lex.zhao.textKeyword.topo.WeightedGraph;

import java.util.*;

/**
 * Created by qtfs on 2018/6/10.
 */
public class LoadBalanceTools {
    public static  Random rand = new Random();

    //算路函数，Random Fit，brandFirstSearch，具有较高的计算性能，速度快
    public static List<Edge> buildPath(WeightedGraph graph, int src, int dst, int threshold) {
        List<Edge> result = new ArrayList<Edge>();
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean[] marked = new boolean[graph.getVertexs()];
        boolean flag = false;
        Edge[] pathTo = new Edge[graph.getVertexs()];
        int[] nodeFrom = new int[graph.getVertexs()];
        queue.add(src);
        marked[src] = true;
        while(!queue.isEmpty() && !flag) {
            int vertex = queue.poll();
            for(Edge e : graph.adj(vertex)) {
               int other_node = (e.get_one_node() != vertex) ? e.get_one_node() : e.get_other_node();
               if(marked[other_node]) continue;
               marked[other_node] = true;
               if(e.getBandwidth() < 0 || e.usageRate() * 100 > threshold ) {
                   marked[other_node] = false;
                   continue;
               }
               pathTo[other_node] = e;
               nodeFrom[other_node] = vertex;
               e.from = vertex;
               if(other_node == dst) {
                   flag = true;
                   break;
               }
                queue.add(other_node);
            }
        }
        if(pathTo[dst] == null)
            return result;
        for(Edge e = pathTo[dst]; e != null; e = pathTo[e.from]) {
            result.add(e);
        }
        return result;
    }

    //算路函数，Random Fit，backingtracking，无目的的回溯导致计算性能大大降低
    public static List<Edge> buildPath_dfs(WeightedGraph graph, int src, int dst, int threshold) {
        List<List<Edge>> paths = new ArrayList<List<Edge>>();
        List<Edge> path = new ArrayList<Edge>();
        boolean[] marked = new boolean[graph.getVertexs()];
        dfs(graph, paths, path, src, dst, marked, threshold);
        return paths.get(0);
    }

    private static void dfs(WeightedGraph graph, List<List<Edge>> paths, List<Edge> path, int node, int dst, boolean[] marked, int threshold) {
        if(paths.size() > 0) return;
        if(node == dst) {
            paths.add(new ArrayList(path));
        }
        else {
            for(Edge e : graph.adj(node)) {
                int other_node = e.get_one_node() != node ? node : e.get_other_node();
                if(marked[other_node]) continue;
                marked[other_node] = true;
                if(e.getBandwidth() < 0 || e.usageRate() * 100 > threshold ) {
                    marked[other_node] = false;
                    continue;
                }
                path.add(e);
                dfs(graph, paths, path, other_node, dst, marked, threshold);
                path.remove(path.size() - 1);
            }
        }
    }
}
