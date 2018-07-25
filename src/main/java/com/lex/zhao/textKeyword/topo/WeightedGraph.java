package com.lex.zhao.textKeyword.topo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by qtfs on 2018/6/5.
 * 拓扑
 */
public class WeightedGraph {

    private int vertexs;
    private int edges;
    private List<Edge>[] adj;
    public List<Edge> edgeSet = new ArrayList<Edge>();
    public WeightedGraph(String fileName) {
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            this.vertexs = scanner.nextInt();
            this.edges = 0;
            adj = (ArrayList<Edge>[]) new ArrayList[vertexs];
            for (int i = 0; i < vertexs; i++)
                adj[i] = new ArrayList<Edge>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] takens = line.split(" ");
                if (takens.length != 3) continue;
                for (int i = 0; i < takens.length; i++)
                    takens[i] = takens[i].trim();
                int node_ip_one = Integer.valueOf(takens[0]);
                int node_ip_other = Integer.valueOf(takens[1]);
                int bandwidth = Integer.valueOf(takens[2]);
                Edge edge = new Edge(node_ip_one, node_ip_other, bandwidth);
                Coordinate.list.add(edge);
                addEdge(edge);
                edgeSet.add(edge);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find file " + fileName);
        }
    }

    public void addEdge(Edge edge) {
        int node_ip_one = edge.get_one_node();
        int node_ip_other = edge.get_other_node();
        adj[node_ip_one].add(edge);
        adj[node_ip_other].add(edge);
        edges++;
    }

    public int getVertexs() {
        return vertexs;
    }

    public int getEdges() {
        return edges;
    }

    public List<Edge> adj(int vertex) {
        return adj[vertex];
    }

    public Iterable<Edge> edges() {
        Set<Edge> lists = new HashSet<Edge>();
        for (int i = 0; i < vertexs; i++) {
            lists.addAll(adj[i]);
        }
        return lists;
    }

}
