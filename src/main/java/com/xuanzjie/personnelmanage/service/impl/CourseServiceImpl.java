package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.mapper.CourseMapper;
import com.xuanzjie.personnelmanage.mapper.CourseUserMapper;
import com.xuanzjie.personnelmanage.pojo.po.Course;
import com.xuanzjie.personnelmanage.pojo.po.CourseUser;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.utils.AuthorityUtils;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import com.xuanzjie.personnelmanage.utils.ResResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseUserMapper courseUserMapper;

    @Override
    public List<CourseListVO> getCourseList(Integer searchType) {
        Integer uid = AuthorityUtils.getUserId();
        if (searchType == null || searchType == 1) {
            return getCourse(1, uid);
        }
        return getCourse(0, uid);
    }


    /**
     * 查询当前登录者创建或者加入的课程
     *
     * @param uid
     * @return
     */
    private List<CourseListVO> getCourse(Integer isCreate, Integer uid) {
        List<CourseUser> courseUserList = courseUserMapper.selectCourseByIdAndCreate(uid, isCreate);
        if (CollectionUtils.isEmpty(courseUserList)) {
            return new ArrayList<>();
        }
        List<Integer> courseIdList = courseUserList.stream().map(CourseUser::getCourseId).collect(Collectors.toList());
        List<Course> courseList = courseMapper.selectCourseByIdList(courseIdList);
        return DozerUtils.mapList(courseList, CourseListVO.class);
    }

    @Override
    public ResResult<EntitySaveVO> updateCourse(Integer id, String name, String explanation, String cover) {
        return null;
    }

    @Override
    public ResResult<EntitySaveVO> getCourseDetail(Integer id) {
        return null;
    }
}
