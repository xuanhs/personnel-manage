package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

@Data
public class CourseInfoVO {

    private Integer id;

    /**
     * 教师名称
     */
    private String teacherName;

    private String name;
    /**
     * 说明
     */
    private Integer explanation;

    /**
     * 封面路径
     */
    private String cover;

}
