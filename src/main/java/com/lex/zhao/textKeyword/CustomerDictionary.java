package com.lex.zhao.textKeyword;

import com.hankcs.hanlp.dictionary.CustomDictionary;

/**
 * Created by qtfs on 2018/5/10.
 */
public class CustomerDictionary {
    public static void addDictionary() {
        CustomDictionary.add("链路", "n 1024");
        CustomDictionary.add("时延", "n 1024");
        CustomDictionary.add("不变", "n 1024");
        CustomDictionary.add("工业级", "a 1024");
        CustomDictionary.remove("一");
        CustomDictionary.add("一个", "d 1024");
        CustomDictionary.add("二", "d 1024");
        CustomDictionary.add("三", "d 1024");
        CustomDictionary.add("四", "d 1024");
        CustomDictionary.add("最好", " 1024");
    }
}