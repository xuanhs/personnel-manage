package com.xuanzjie.personnelmanage.comman;

import lombok.Getter;

public enum ResultCode {

    NAME_REPEAT(1001, "用户名已存在"),
    INPUT_EXCEPTION(1002,"参数异常"),
    SUCCESS(0,"成功"),
    FAIL(1,"发生错误"),
    PASSWORD_FAIL(1003,"密码错误");
    @Getter
    private Integer code;

    @Getter
    private String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
