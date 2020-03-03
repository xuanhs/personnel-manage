package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.CourseServiceApi;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController implements CourseServiceApi {
    @Override
    public ResResult<CourseListVO> getCreateCourse(CourseDTO.GetCourseListDTO getCourseListDTO) {
        return null;
    }

    @Override
    public ResResult<EntitySaveVO> updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO) {
        return null;
    }

    @Override
    public ResResult<EntitySaveVO> getCourseDetail(CourseDTO.GetCourseDetailDTO getCourseDetailDTO) {
        return null;
    }
}
