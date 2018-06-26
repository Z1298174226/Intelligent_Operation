package com.lex.zhao.textKeyword.intentionCollection;

import com.hankcs.hanlp.dictionary.CustomDictionary;

/**
 * Created by qtfs on 2018/5/10.
 */
public class CustomerDictionary {
    public static void addDictionary() {
        CustomDictionary.add("链路", "n 1024");
        CustomDictionary.add("时延", "n 1024");
        CustomDictionary.add("全网", "n 1024");
        CustomDictionary.add("科研楼", "n 1024");
        CustomDictionary.add("在线看", "n 1024");
        CustomDictionary.add("不变", "a 1024");
        CustomDictionary.add("工业级", "a 1024");
        CustomDictionary.add("低于", "a 1024");
        CustomDictionary.add("不超过", "a 1024");
        CustomDictionary.add("不高于", "a 1024");
        CustomDictionary.add("不大于", "a 1024");
        CustomDictionary.add("高于", "a 1024");
        CustomDictionary.remove("一");
        CustomDictionary.add("一个", "d 1024");
        CustomDictionary.add("二", "d 1024");
        CustomDictionary.add("三", "d 1024");
        CustomDictionary.add("四", "d 1024");
        CustomDictionary.add("最好", " 1024");
        CustomDictionary.add("下发", "v 1024");
    }
}
