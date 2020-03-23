package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseInfoVO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseManageVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;

import java.util.List;

public interface CourseService {

    List<CourseListVO> getCourseList(Integer searchType);

    EntitySaveVO updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO);

    CourseInfoVO getCourseDetail(Integer id);
}
