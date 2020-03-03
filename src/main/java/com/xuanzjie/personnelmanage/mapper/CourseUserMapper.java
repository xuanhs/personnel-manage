package com.xuanzjie.personnelmanage.mapper;

import com.xuanzjie.personnelmanage.pojo.po.Course;
import com.xuanzjie.personnelmanage.pojo.po.CourseUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CourseUserMapper extends Mapper<CourseUser> {


    @Select("select *  from course_user where is_create = #{isCreate} and id = #{id}")
    List<CourseUser> selectCourseByIdAndCreate(@Param("id") Integer id,@Param("isCreate") Integer isCreate);
}
