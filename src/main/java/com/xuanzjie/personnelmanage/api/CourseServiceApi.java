package com.xuanzjie.personnelmanage.api;


import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.*;
import com.xuanzjie.personnelmanage.utils.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "personnel-manage")
@Api(tags = "课程模块")
public interface CourseServiceApi {

    /**
     * 获取课程列表
     * @param getCourseListDTO
     * @return CourseListVO
     */
    @ApiOperation(value = "课程列表", response = CourseListVO.class)
    @PostMapping("/course/getCourseList")
    ResResult<List<CourseListVO>> getCreateCourse(CourseDTO.GetCourseListDTO getCourseListDTO);

    /**
     * 新增/更新课程
     * @param updateCourseDTO
     * @return
     */
    @PostMapping("/course/updateCourse")
    ResResult<EntitySaveVO> updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO);

    /**
     * 获取课程详情
     * @param getCourseDetailDTO
     * @return
     */
    @PostMapping("/course/getCourseDetail")
    ResResult<CourseInfoVO> getCourseDetail(CourseDTO.GetCourseDetailDTO getCourseDetailDTO);


}
