package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClassManageVO {

    private Integer id;

    /**
     * 课程id
     */
    private Integer courseId;

    private String name;

    private Integer createTime;

    /**
     * 学生
     */
    private List<UserListVO> userListVOList;

    public void setDefaultCourse(Integer id, Integer courseId, String name, Integer createTime, List<UserListVO> userListVOList) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.createTime = createTime;
        this.userListVOList = userListVOList;
    }
}
