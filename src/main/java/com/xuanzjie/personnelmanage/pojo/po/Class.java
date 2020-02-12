package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 班级表
 */
@Data
@Table(name = "class")
public class Class {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Integer createTime;
}
