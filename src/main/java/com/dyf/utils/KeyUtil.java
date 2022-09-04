package com.dyf.utils;

import java.util.Random;

public class KeyUtil
{
    public static synchronized String genUniqueKey()
    {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000; // 生成6位的随机数
        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static synchronized String genUniqueFoodKey()
    {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000; // 生成4位的随机数
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
