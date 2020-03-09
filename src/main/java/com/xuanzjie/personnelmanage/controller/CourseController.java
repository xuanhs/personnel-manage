package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.CourseServiceApi;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseInfoVO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.utils.ResResult;
import com.xuanzjie.personnelmanage.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController implements CourseServiceApi {

    @Autowired
    CourseService courseService;

    @Override
    public ResResult<List<CourseListVO>> getCreateCourse(@RequestBody @Validated CourseDTO.GetCourseListDTO getCourseListDTO) {
        return ResUtils.data(courseService.getCourseList(getCourseListDTO.getSearchType()));
    }

    @Override
    public ResResult<EntitySaveVO> updateCourse(@RequestBody @Validated CourseDTO.UpdateCourseDTO updateCourseDTO) {
        return ResUtils.data(courseService.updateCourse(updateCourseDTO));
    }

    @Override
    public ResResult<CourseInfoVO> getCourseDetail(@RequestBody @Validated CourseDTO.GetCourseDetailDTO getCourseDetailDTO) {
        return ResUtils.data(courseService.getCourseDetail(getCourseDetailDTO.getId()));
    }
}
