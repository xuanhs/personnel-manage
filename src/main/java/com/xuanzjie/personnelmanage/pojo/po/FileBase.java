package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件表
 */
@Data
@Table(name = "file")
public class FileBase {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 文件类似 0学生，1教师
     */
    @Column(name = "type")
    private Integer type;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "experiment_id")
    private Integer experimentId;

    /**
     * 原文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 存储文件名
     */
    @Column(name = "memory_name")
    private String memoryName;

    @Column(name = "create_time")
    private Integer createTime;

    @Column(name = "update_time")
    private Integer updateTime;
}
