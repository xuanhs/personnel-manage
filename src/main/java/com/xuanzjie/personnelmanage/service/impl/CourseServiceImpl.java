package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.mapper.CourseMapper;
import com.xuanzjie.personnelmanage.mapper.CourseUserMapper;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.po.Course;
import com.xuanzjie.personnelmanage.pojo.po.CourseUser;
import com.xuanzjie.personnelmanage.pojo.vo.CourseInfoVO;
import com.xuanzjie.personnelmanage.pojo.vo.CourseListVO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.utils.AuthorityUtils;
import com.xuanzjie.personnelmanage.utils.DateUtils;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
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
     * isCreate = 0时搜索加入的课程，isCreate = 1搜索创建的课程
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
    public EntitySaveVO updateCourse(CourseDTO.UpdateCourseDTO updateCourseDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setCode(1);
        if (updateCourseDTO == null) {
            entitySaveVO.setCode(0);
            return entitySaveVO;
        }
        try {
            Course course = DozerUtils.map(updateCourseDTO, Course.class);
            if (updateCourseDTO.getId() == null || updateCourseDTO.getId() <= 0) {
                addCourse(course);
                return entitySaveVO;
            }
            updateCourseById(course);
        } catch (Exception e) {
            log.warn("新增/修改课程出错，e:{}", e);
            entitySaveVO.setCode(0);
        }
        return entitySaveVO;
    }

    @Override
    public CourseInfoVO getCourseDetail(Integer id) {
        if (id == null) {
            return new CourseInfoVO();
        }
        return searchCourseById(id);
    }

    private CourseInfoVO searchCourseById(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null) {
            return new CourseInfoVO();
        }
        return DozerUtils.map(course, CourseInfoVO.class);
    }

    /**
     * 新增课程
     *
     * @param course
     */
    private void addCourse(Course course) {
        course.setTeacherId(AuthorityUtils.getUserId());
        courseMapper.insertSelective(course);
        log.info("新增课程成功 {}", course);
        CourseUser courseUser = new CourseUser();
        courseUser.setCourseId(course.getId());
        courseUser.setIsCreate(1);
        courseUser.setUserId(AuthorityUtils.getUserId());
        courseUser.setCreateTime(DateUtils.currentTimeSeconds());
        courseUser.setUpdateTime(DateUtils.currentTimeSeconds());
        courseUserMapper.insertSelective(courseUser);
        log.info("课程-用户关联表插入数据成功 {}", courseUser);
    }


    /**
     * 更新课程
     *
     * @param course
     */
    private void updateCourseById(Course course) {
        courseMapper.updateByPrimaryKey(course);
    }

}
