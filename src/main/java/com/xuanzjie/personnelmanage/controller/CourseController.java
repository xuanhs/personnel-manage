package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.CourseServiceApi;
import com.xuanzjie.personnelmanage.pojo.dto.ApplyDTO;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.*;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.utils.ResResult;
import com.xuanzjie.personnelmanage.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class  CourseController implements CourseServiceApi {

    @Autowired
    CourseService courseService;

    @Override
    public ResResult<List<CourseListVO>> getCreateCourse(@RequestBody @Validated CourseDTO.GetCourseListDTO getCourseListDTO) {
        return ResUtils.data(courseService.getCourseList(getCourseListDTO.getSearchType(),getCourseListDTO.getName()));
    }

    @Override
    public ResResult<EntitySaveVO> updateCourse(@RequestBody @Validated CourseDTO.UpdateCourseDTO updateCourseDTO) {
        return ResUtils.data(courseService.updateCourse(updateCourseDTO));
    }

    @Override
    public ResResult<CourseInfoVO> getCourseDetail(@RequestBody @Validated CourseDTO.GetCourseDetailDTO getCourseDetailDTO) {
        return ResUtils.data(courseService.getCourseDetail(getCourseDetailDTO.getId()));
    }

    @Override
    public ResResult<List<ClassManageVO>> getCourseClass(@RequestBody @Validated CourseDTO.GetCourseDetailDTO getCourseDetailDTO) {
        return ResUtils.data(courseService.searchClassList(getCourseDetailDTO.getId()));
    }

    @Override
    public ResResult<EntitySaveVO> updateClass(@RequestBody @Validated CourseDTO.UpdateClassDTO updateClassDTO) {
        return ResUtils.data(courseService.updateClass(updateClassDTO));
    }

    @Override
    public ResResult<EntitySaveVO> updateStudent(@RequestBody @Validated CourseDTO.UpdateStudentDTO updateStudentDTO) {
        return ResUtils.data(courseService.updateStudent(updateStudentDTO));
    }

    @Override
    public ResResult<List<CourseListVO>> searchCourse(@RequestBody @Validated CourseDTO.SearchCourseDTO searchCourseDTO) {
        return ResUtils.data(courseService.searchCourse(searchCourseDTO));
    }

    @Override
    public ResResult<EntitySaveVO> applyCourse(@RequestBody @Validated CourseDTO.ApplyCourseDTO applyCourseDTO) {
        return ResUtils.data(courseService.applyCourse(applyCourseDTO));
    }

    @Override
    public ResResult<EntitySaveVO> dealApply(@RequestBody @Validated CourseDTO.DealCourseDTO dealCourseDTO) {
        return ResUtils.data(courseService.dealApply(dealCourseDTO));
    }

    @Override
    public ResResult<List<ApplyMessageVO>> searchApplyMessage(@RequestBody @Validated ApplyDTO.SearchApplyMessageDTO req) {
        return ResUtils.data(courseService.searchApplyMessage(req));
    }
}
