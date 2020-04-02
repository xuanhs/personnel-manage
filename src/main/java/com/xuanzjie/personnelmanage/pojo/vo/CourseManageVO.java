package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CourseManageVO {

    private Integer id;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 名称
     */
    private String name;

    /**
     * 说明
     */
    private String explanation;

    /**
     * 封面路径
     */
    private Integer FileBase;


    /**
     *班级列表
     */
    private List<ClassManageVO> classVOList;
}
