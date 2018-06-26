package com.lex.zhao.textKeyword.networkconf;

import com.lex.zhao.textKeyword.bussiness.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qtfs on 2018/6/21.
 */
public class ProtocolToPriority {
    public static Map<Type, Integer> protocolMap = new HashMap<Type, Integer>();
    static{
        //定义协议对应端口
        protocolMap.put(Type.HTTP, 1);
        protocolMap.put(Type.HTTPS, 1);
        protocolMap.put(Type.P2P, 3);
        protocolMap.put(Type.FTP, 3);
        protocolMap.put(Type.ELSE,2);
    }
}
