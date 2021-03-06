package com.lex.zhao.textKeyword;


import com.lex.zhao.textKeyword.bussiness.Type;
import com.lex.zhao.textKeyword.intentionCollection.TextKeyWord;
import com.lex.zhao.textKeyword.networkconf.Enums;

import java.util.List;
import java.util.Scanner;
import java.util.Map;
/**
 * Created by qtfs on 2018/5/30.
 * 主控制台
 */
public class Console {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String content = scanner.nextLine();
            List<String> wordList = new TextKeyWord().getKeyword("", content);
            Map<String, Integer> resultMap = new ParameterMap().parameterMap(wordList);
            new StrategyChoice().strategyChoice(resultMap);
        }
    }
}
