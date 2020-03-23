package com.xuanzjie.personnelmanage.service.impl;

import com.netflix.discovery.converters.Auto;
import com.xuanzjie.personnelmanage.mapper.CourseMapper;
import com.xuanzjie.personnelmanage.mapper.CourseUserMapper;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.po.Course;
import com.xuanzjie.personnelmanage.pojo.po.CourseUser;
import com.xuanzjie.personnelmanage.pojo.po.User;
import com.xuanzjie.personnelmanage.pojo.vo.*;
import com.xuanzjie.personnelmanage.search.ExampleBuilder;
import com.xuanzjie.personnelmanage.search.Search;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.service.UserInfoService;
import com.xuanzjie.personnelmanage.utils.AuthorityUtils;
import com.xuanzjie.personnelmanage.utils.DateUtils;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseUserMapper courseUserMapper;

    @Autowired
    UserInfoService userInfoService;

    @Override
    public List<CourseListVO> getCourseList(Integer searchType) {
        Integer uid = AuthorityUtils.getUserId();
        log.info("查询课程：seatchType:{}",searchType);
        if (searchType != null && searchType == 1) {
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
        Search search = new Search();
        search.put("isCreate_eq",isCreate);
        search.put("userId_eq",uid);
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(courseUserList)) {
            return new ArrayList<>();
        }
        List<Integer> courseIdList = courseUserList.stream().map(CourseUser::getCourseId).collect(Collectors.toList());

        List<Course> courseList = selectCourseByIdList(courseIdList);
        List<CourseListVO> result = searchTeacherName(courseList);
        log.info("查询成功课程成功，isCreate:{},result:{}",isCreate,result);
        return result;
    }

    /**
     * 根据course 转化为courseListVO
     * @param courseList
     * @return
     */
    private List<CourseListVO> searchTeacherName(List<Course> courseList) {
        Set<Integer> teacherIdList = courseList.stream().map(Course::getTeacherId).collect(Collectors.toSet());
        List<Integer> idList = new ArrayList<>(teacherIdList.size()+1);
        idList.addAll(teacherIdList);
        List<User> userList = userInfoService.searchUserList(idList);
        Map<Integer,String> teacherNameMap = userList.stream().collect(Collectors.toMap(User::getId,User::getName));
        List<CourseListVO> result = new ArrayList<>(courseList.size()+1);
        for(Course course : courseList){
            CourseListVO courseListVO = DozerUtils.map(course,CourseListVO.class);
            courseListVO.setTeacherName(teacherNameMap.containsKey(course.getTeacherId())?
                    teacherNameMap.get(course.getTeacherId()):"");
            result.add(courseListVO);
        }
        return result;
    }

    /**
     *根据课程id批量查询课程
     * @param courseIdList
     * @return
     */
    private List<Course> selectCourseByIdList(List<Integer> courseIdList) {
        Search search = new Search();
        search.put("id_in",courseIdList);
        Example example = new ExampleBuilder(Course.class).search(search).build();
        List<Course> courseList = courseMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courseIdList)){
            return new ArrayList<>();
        }
        return courseList;
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
        //课程信息
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null) {
            return new CourseInfoVO();
        }
        //班级信息 todo
        List<ClassManageVO> classManageVOList = searchClassAndStudent(id);
        //实验信息 todo
        //动态 todo
        //统计信息 todo
        //

        return DozerUtils.map(course, CourseInfoVO.class);
    }

    /**
     * 根据课程id获取对应班级和学生
     * @param courseId
     * @return List<ClassManageVO>
     */
    private List<ClassManageVO> searchClassAndStudent(Integer courseId) {
        List<ClassManageVO> result = new ArrayList<>(4);
        // 获取默认班级学生 todo
        result.add(searchDefaultClass(courseId));
        // 获取其余班级 todo
        // 获取对应班级学生 todo

        return result;
    }

    /**
     * 获取课程默认班级
     * @param courseId
     * @return
     */
    private ClassManageVO searchDefaultClass(Integer courseId) {
        ClassManageVO result = new ClassManageVO();
        Search search = new Search();
        search.put("courseId_eq",courseId);
        search.put("classId_eq",0);
        search.put("isCreate_eq",0);
        search.put("status_eq",1);
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courseUserList)){
            return result;
        }
        List<UserListVO> userListVOList = new ArrayList<>(courseUserList.size()+1);
        result.setDefaultCourse(0,courseId,"默认班级",0,userListVOList);
        return  result;
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
