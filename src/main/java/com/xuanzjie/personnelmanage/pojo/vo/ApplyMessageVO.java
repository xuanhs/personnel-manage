package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

/**
 * 课程申请信息
 */
@Data
public class ApplyMessageVO {

    private Integer id;

    private Integer courseId;

    private Integer userId;

    private String name;

    private String phone;

    private Integer status;

    private Integer updateTime;

    private Integer createTime;
}
