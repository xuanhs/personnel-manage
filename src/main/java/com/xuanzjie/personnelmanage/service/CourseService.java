package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;

import java.util.List;

public interface CourseService {

    List<CourseListVO> getCourseList(Integer searchType);

    ResResult<EntitySaveVO> updateCourse(Integer id, String name, String explanation, String cover);

    ResResult<EntitySaveVO> getCourseDetail(Integer id);
}
