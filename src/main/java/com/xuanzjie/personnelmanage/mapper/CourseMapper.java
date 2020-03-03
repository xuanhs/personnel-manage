package com.xuanzjie.personnelmanage.mapper;

import com.xuanzjie.personnelmanage.pojo.po.Course;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CourseMapper extends Mapper<Course> {

    @Select("select * from course where id in #{ids}")
    public List<Course> selectCourseByIdList(@Param("ids") List<Integer> idList);
}
