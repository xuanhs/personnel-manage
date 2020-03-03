package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "course_user")
public class CourseUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "is_create")
    private Integer isCreate;

    @Column(name = "create_time")
    private Integer createTime;

    @Column(name = "update_time")
    private Integer updateTime;
}
