package com.lex.zhao.textKeyword.networkconf;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by qtfs on 2018/6/20.
 */
public class ProtocolToPort
{
    public static Map<String, Integer> protocolMap = new HashMap<String, Integer>();
    static{
        //定义协议对应端口
        protocolMap.put("HTTP", 80);
        protocolMap.put("HTTPS", 443);
        protocolMap.put("FTP", 21);
        protocolMap.put("Telnet", 23);
    }
}
