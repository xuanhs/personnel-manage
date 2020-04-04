package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

@Data
public class CourseListVO {

    private Integer id;

    /**
     * 教师名称
     */
    private String teacherName;

    private String name;

    private Integer createTime;

    /**
     * 说明
     */
    private String explanation;

    /**
     * 封面文件id
     */
    private Integer fileId;

    /**
     * 封面路径
     */
    private FileBaseVO fileBase;

}
