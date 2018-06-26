package com.lex.zhao.textKeyword;

/**
 * Created by qtfs on 2018/5/30.
 */

import com.lex.zhao.textKeyword.networkconf.ProtocolToPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParameterMap {
    public  Map<String, Integer> parameterMap(List<String> wordList) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for(int i = 0; i < wordList.size(); i++) {
            if(wordList.get(i).equals("带宽")) {
                if((i > 0 && wordList.get(i - 1).equals("高") )|| (i + 1 < wordList.size() && wordList.get(i + 1).equals("高")))
                    result.put("bandwidth", 30);
                if(i + 1 < wordList.size() && isInteger(wordList.get(i + 1)))
                    result.put("bandwidth", Integer.valueOf(wordList.get(i + 1)));
                if(i + 2 < wordList.size() && isInteger(wordList.get(i + 2)))
                    result.put("bandwidth", Integer.valueOf(wordList.get(i + 2)));
            }
            if(wordList.get(i).equals("利用率")) {
                if (i + 1 < wordList.size() && (wordList.get(i + 1).equals("不超过") || wordList.get(i + 1).equals("不高于") ||
                        wordList.get(i + 1).equals("不大于")) ) {
                    if (i + 2 < wordList.size() && isInteger(wordList.get(i + 2)))
                        result.put("utilizationRate", Integer.valueOf(wordList.get(i + 2)));
                }
            }
            if(wordList.get(i).equals("新")) {
                if (i + 1 < wordList.size() && (wordList.get(i + 1).equals("服务器")))  {
                    if (i + 2 < wordList.size() && isProtocol(wordList.get(i + 2))) {
                        if(ProtocolToPort.protocolMap.get(wordList.get(i + 2)) == null)
                            System.out.println("Can't resolve the protocol");
                        else
                            result.put("serverConf", Integer.valueOf(ProtocolToPort.protocolMap.get(wordList.get(i + 2))));
                    }
                }
            }
        }
        return result;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isProtocol(String str) {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        return pattern.matcher(str).matches();
    }
}
