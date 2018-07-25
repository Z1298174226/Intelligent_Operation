package com.lex.zhao.textKeyword.topo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by qtfs on 2018/7/24.
 */
public class Coordinate {
    public static Map<Integer, Double>  coordinate = new HashMap<Integer, Double>();
    public static List<Edge> list = new ArrayList<Edge>();
    static{
        coordinate.put(0, new Double(0, -100));
        coordinate.put(1, new Double(-100, 0));
        coordinate.put(2, new Double(100, 0));
        coordinate.put(3, new Double(-100, -200));
        coordinate.put(4, new Double(100, -200));
        coordinate.put(5, new Double(200, -100));
        coordinate.put(6, new Double(300, 0));
        coordinate.put(7, new Double(200, 100));
        coordinate.put(8, new Double(-200, 100));
        coordinate.put(9, new Double(-300, 0));
        coordinate.put(10, new Double(-200, -100));
    }
}
