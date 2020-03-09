package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 课程表
 */
@Data
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 教师id
     */
    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Integer createTime;

    /**
     * 说明
     */
    @Column(name = "explanation")
    private String explanation;

    /**
     * 封面路径
     */
    @Column(name = "cover")
    private String cover;
}
