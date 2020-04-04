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
    @ApiOperation(value = "获取课程详情")
    @PostMapping("/course/getCourseDetail")
    ResResult<CourseInfoVO> getCourseDetail(CourseDTO.GetCourseDetailDTO getCourseDetailDTO);

    @ApiOperation(value = "获取课程班级")
    @PostMapping("/course/getCourseClass")
    ResResult<List<ClassManageVO>> getCourseClass(CourseDTO.GetCourseDetailDTO getCourseDetailDTO);

    /**
     * 创建、更新班级
     *
     * @param updateClassDTO
     * @return
     */
    @ApiOperation(value = "创建、更新班级")
    @PostMapping("/course/updateClass")
    ResResult<EntitySaveVO> updateClass(CourseDTO.UpdateClassDTO updateClassDTO);

    /**
     * 创建、更新学生
     *
     * @param updateStudentDTO
     * @return
     */
    @ApiOperation(value = "创建、更新学生")
    @PostMapping("/course/updateStudent")
    ResResult<EntitySaveVO> updateStudent(CourseDTO.UpdateStudentDTO updateStudentDTO);

    @ApiOperation(value = "根据名称搜索课程")
    @PostMapping("/course/searchCourse")
    ResResult<List<CourseListVO>> searchCourse(CourseDTO.SearchCourseDTO searchCourseDTO);

}
