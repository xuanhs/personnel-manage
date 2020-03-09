package com.xuanzjie.personnelmanage.utils;

public class DateUtils {
    /**
     * 获取当前时间戳，11位
     *
     * @return
     */
    public static int currentTimeSeconds() {
        return Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));
    }
}
