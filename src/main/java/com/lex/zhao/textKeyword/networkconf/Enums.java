package com.lex.zhao.textKeyword.networkconf;

import java.util.Random;

/**
 * Created by qtfs on 2018/6/26.
 */
public class Enums {
    private static int random = (int)(Math.random() * 10);// 生成种子

    private static Random rand = new Random(random);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}
