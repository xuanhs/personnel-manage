package com.xuanzjie.personnelmanage.utils;

public class ResUtils {
    private static final String SUC_DESC = Boolean.TRUE.toString();
    private static final int SUC = 0;
    private static final int FAIL = 10000;

    /**
     * 返回对象类型或者基本类型
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResResult<T> data(T data) {
        return result(SUC, SUC_DESC, data);
    }

    public static <T> ResResult<T> data(Integer code, T data) {
        return result(code, SUC_DESC, data);
    }

    public static <T> ResResult<T> fail(T data) {
        return result(FAIL, SUC_DESC, data);
    }

    private static <T> ResResult<T> result(int code, String desc, T data) {
        return new ResResult<>(code, desc, data);
    }
}
