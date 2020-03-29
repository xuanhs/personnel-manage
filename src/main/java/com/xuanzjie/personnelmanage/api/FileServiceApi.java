package com.xuanzjie.personnelmanage.api;

import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "personnel-manage")
public interface FileServiceApi {

    /**
     * 新增/更新课程
     * @param updateCourseDTO
     * @return
     */
    @PostMapping("/course/updateCourse")
    ResResult<EntitySaveVO> updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO);
}
