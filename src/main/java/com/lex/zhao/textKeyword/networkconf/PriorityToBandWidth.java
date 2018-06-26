package com.lex.zhao.textKeyword.networkconf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qtfs on 2018/6/26.
 */
public class PriorityToBandWidth {
    public static Map<Integer, Integer> bandWidthMap = new HashMap<Integer, Integer>();
    static{
        //定义协议对应端口
        bandWidthMap.put(3, 2);
        bandWidthMap.put(2, 3);
        bandWidthMap.put(1, 5);
    }
}
