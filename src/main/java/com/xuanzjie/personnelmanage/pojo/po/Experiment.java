package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实验/课时表
 */
@Data
@Table(name = "experiment")
public class Experiment {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Integer createTime;
}
