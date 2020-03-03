package com.xuanzjie.personnelmanage.api;


import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "personnel-manage")
public interface CourseServiceApi {

    /**
     * 获取课程列表
     * @param getCourseListDTO
     * @return CourseListVO
     */
    @PostMapping("getCourseList")
    ResResult<CourseListVO> getCreateCourse(CourseDTO.GetCourseListDTO getCourseListDTO);

    /**
     * 新增/更新课程
     * @param updateCourseDTO
     * @return
     */
    @PostMapping("updateCourse")
    ResResult<EntitySaveVO> updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO);

    /**
     * 获取课程详情
     * @param getCourseDetailDTO
     * @return
     */
    @PostMapping("getCourseDetail")
    ResResult<EntitySaveVO> getCourseDetail(CourseDTO.GetCourseDetailDTO getCourseDetailDTO);
}
