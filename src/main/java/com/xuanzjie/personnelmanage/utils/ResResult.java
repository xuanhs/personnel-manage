package com.xuanzjie.personnelmanage.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResResult<T> {
    private Integer status;
    private String desc;
    /**
     * 返回的数据可以是对象，也可以是单个基本类型
     * 如果返回的是对象，后期增加属性是不影响下游调用
     * 如果返回的是单个基本类型，那么相当于确定这个接口的返回数据不能修改，否则会影响到下游调用方
     */
    private T data;
}