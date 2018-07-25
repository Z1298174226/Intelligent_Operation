package com.lex.zhao.textKeyword.JsonTools;

import com.lex.zhao.textKeyword.topo.Coordinate;
import com.lex.zhao.textKeyword.topo.Edge;
import com.lex.zhao.textKeyword.topo.WeightedGraph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Stack;

/**
 * Created by qtfs on 2018/7/10.
 */
public class JsonTools {
    public static void createKeyWords(List<String> wordList) {
        JSONObject json = new JSONObject();
        try {
            JSONArray keywords = new JSONArray();
            for(int i = 0; i < wordList.size(); i++) {
                JSONObject keyword = new JSONObject();
                keyword.put("id", 0);
                keyword.put("content", wordList.get(i));
                keywords.put(keyword);
            }
            json.put("keywords", keywords);
        }catch(JSONException ex) {

        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("src\\main\\resources\\keywords.json"))));
            writer.write(String.valueOf(json));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createPolicy(String policyString) {
        JSONObject json = new JSONObject();
        try {
            JSONArray policies = new JSONArray();
            JSONObject policy = new JSONObject();
            policy.put("content", policyString);
            policies.put(policy);
            json.put("policy", policies);
        }catch(JSONException ex) {

        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("src\\main\\resources\\policy.json"))));
            writer.write(String.valueOf(json));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createBuildPath(WeightedGraph graph, List<Edge> list, int src, int dst) {
        JSONObject json = new JSONObject();
        try {
            JSONArray links = new JSONArray();
            JSONArray nodes = new JSONArray();
            JSONArray bandwidths = new JSONArray();
            JSONObject srcJson = new JSONObject();
            srcJson.put("name", String.valueOf("node" + src));
            srcJson.put("id", src);
            srcJson.put("x", Coordinate.coordinate.get(src).getX());
            srcJson.put("y", Coordinate.coordinate.get(src).getY());
            nodes.put(srcJson);

            int from = src;
            int to = dst;
            for(int i = list.size() - 1; i >= 0; i--) {
                Edge e = list.get(i);

                JSONObject link = new JSONObject();
                JSONObject dstJson = new JSONObject();

                from = (e.get_one_node() == from ? e.get_one_node() : e.get_other_node());
                link.put("from", from);
                to = (e.get_one_node() == from ? e.get_other_node() : e.get_one_node());
                link.put("to", to);
                from = to;
                dstJson.put("name", String.valueOf("node" + to));
                dstJson.put("id", to);
                dstJson.put("x", Coordinate.coordinate.get(to).getX());
                dstJson.put("y", Coordinate.coordinate.get(to).getY());
                links.put(link);
                nodes.put(dstJson);
            }
            for(int i = 0; i < graph.edgeSet.size(); i++) {
                JSONObject bandwidth = new JSONObject();
                bandwidth.put("num", i + 1);
                bandwidth.put("value", graph.edgeSet.get(i).getBandwidth());
                bandwidths.put(bandwidth);
            }
            json.put("nodes", nodes);
            json.put("relations", links);
            json.put("bandwidth", bandwidths);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("src\\main\\resources\\buildPath.json"))));
            writer.write(String.valueOf(json));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
