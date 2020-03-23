package com.xuanzjie.personnelmanage.pojo.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class FileBaseVO {

    private Integer id;

    /**
     * 文件类似 0学生，1教师
     */
    private Integer type;

    private Integer courseId;

    private Integer classId;

    private Integer experimentId;

    /**
     * 原文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 存储文件名
     */
    private String memoryName;

    private Integer createTime;

    @Column(name = "update_time")
    private Integer updateTime;
}
