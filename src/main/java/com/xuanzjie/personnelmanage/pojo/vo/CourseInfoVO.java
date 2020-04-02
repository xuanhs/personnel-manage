package com.xuanzjie.personnelmanage.pojo.vo;

import com.xuanzjie.personnelmanage.pojo.po.FileBase;
import lombok.Data;

import java.util.List;

@Data
public class CourseInfoVO {

    private Integer id;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 课程名称
     */
    private String name;
    /**
     * 说明
     */
    private String explanation;

    /**
     * 封面路径
     */
    private FileBase fileBase;

    /**
     * 班级、学生
     */
    private List<ClassManageVO> classManageVOList;

}
