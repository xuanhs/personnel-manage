package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.po.FileBase;
import com.xuanzjie.personnelmanage.pojo.vo.*;

import java.util.List;

public interface CourseService {

    /**
     * 获取课程列表
     *
     * @param searchType
     * @return
     */
    List<CourseListVO> getCourseList(Integer searchType,String name);

    /**
     * 创建、更新课程
     *
     * @param updateCourseDTO
     * @return
     */
    EntitySaveVO updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO);

    /**
     * 获取课程详情
     *
     * @param id
     * @return
     */
    CourseInfoVO getCourseDetail(Integer id);

    /**
     * 获取班级学生
     * @param id
     * @return
     */
    List<ClassManageVO> searchClassList(Integer id);

    /**
     *
     * @param courseId
     * @param classId
     * @return
     */
    List<FileBaseVO> searchExperiment(Integer courseId,Integer classId);


    /**
     * 创建、更新班级
     *
     * @param updateClassDTO
     * @return
     */
    EntitySaveVO updateClass(CourseDTO.UpdateClassDTO updateClassDTO);

    /**
     * 创建、更新学生
     *
     * @param updateStudentDTO
     * @return
     */
    EntitySaveVO updateStudent(CourseDTO.UpdateStudentDTO updateStudentDTO);

    /**
     * 搜索课程、教师
     */
    List<CourseListVO> searchCourse(CourseDTO.SearchCourseDTO searchCourseDTO);

    /**
     * 学生申请加入课程
     */
    EntitySaveVO applyCourse(CourseDTO.ApplyCourseDTO applyCourseDTO);

    /**
     * 教师同意驳回加入课程
     */
    EntitySaveVO dealApply(CourseDTO.DealCourseDTO dealCourseDTO);
}
