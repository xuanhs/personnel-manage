package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.mapper.ClassMapper;
import com.xuanzjie.personnelmanage.mapper.CourseMapper;
import com.xuanzjie.personnelmanage.mapper.CourseUserMapper;
import com.xuanzjie.personnelmanage.pojo.dto.ApplyDTO;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.po.Class;
import com.xuanzjie.personnelmanage.pojo.po.Course;
import com.xuanzjie.personnelmanage.pojo.po.CourseUser;
import com.xuanzjie.personnelmanage.pojo.po.User;
import com.xuanzjie.personnelmanage.pojo.vo.*;
import com.xuanzjie.personnelmanage.search.ExampleBuilder;
import com.xuanzjie.personnelmanage.search.Search;
import com.xuanzjie.personnelmanage.service.CourseService;
import com.xuanzjie.personnelmanage.service.FileService;
import com.xuanzjie.personnelmanage.service.UserInfoService;
import com.xuanzjie.personnelmanage.utils.AuthorityUtils;
import com.xuanzjie.personnelmanage.utils.DateUtils;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
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

    @Autowired
    ClassMapper classMapper;

    @Autowired
    FileService fileService;

    @Override
    public List<CourseListVO> getCourseList(Integer searchType, String name) {
        Integer uid = AuthorityUtils.getUserId();
        log.info("查询课程：searchType:{}", searchType);
        if (StringUtils.isEmpty(name)) {
            name = "";
        }
        if (searchType != null && searchType == 1) {
            return getCourse(1, uid, name);
        }
        if (searchType != null && searchType == 0) {
            return getCourse(0, uid, name);
        }
        return getAllCourse(uid);
    }

    /**
     * 获取所有的课程，不包括登录者创建的，以及已经加入的
     *
     * @param uid
     * @return
     */
    private List<CourseListVO> getAllCourse(Integer uid) {
        Search search = new Search();
        search.put("teacherId_ne", uid);
        Example example = new ExampleBuilder(Course.class).search(search).build();
        List<Course> courseList = courseMapper.selectByExample(example);
        log.info("查询更多课程成功，result:{}", courseList);
        if (CollectionUtils.isEmpty(courseList)) {
            return new ArrayList<>(0);
        }
        List<CourseListVO> result = searchTeacherName(courseList);
        searchFileBase(result);
        return result;
    }


    /**
     * 查询当前登录者创建或者加入的课程
     * isCreate = 0时搜索加入的课程，isCreate = 1搜索创建的课程
     *
     * @param uid
     * @return
     */
    private List<CourseListVO> getCourse(Integer isCreate, Integer uid, String name) {
        Search search = new Search();
        search.put("isCreate_eq", isCreate);
        search.put("userId_eq", uid);
        search.put("status_eq", 1);
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(courseUserList)) {
            return new ArrayList<>();
        }
        List<Integer> courseIdList = courseUserList.stream().map(CourseUser::getCourseId).collect(Collectors.toList());

        List<Course> courseList = selectCourseByIdList(courseIdList, name);
        List<CourseListVO> result = searchTeacherName(courseList);
        searchFileBase(result);
        log.info("查询成功课程成功，isCreate:{},result:{}", isCreate, result);
        return result;
    }

    /**
     * 根据文件id搜索文件
     *
     * @param result
     */
    private void searchFileBase(List<CourseListVO> result) {
        List<Integer> fileIdList = result.stream().map(CourseListVO::getFileId).collect(Collectors.toList());
        List<FileBaseVO> fileBaseList = fileService.searchFileListByIds(fileIdList);
        Map<Integer, FileBaseVO> fileBaseMap = fileBaseList.stream().
                collect(Collectors.toMap(FileBaseVO::getId, fileBaseVO -> fileBaseVO, (newData, oldData) -> newData));
        for (CourseListVO courseListVO : result) {
            courseListVO.setFileBase(fileBaseMap.get(courseListVO.getFileId()));
        }
    }

    /**
     * 根据course 转化为courseListVO
     *
     * @param courseList
     * @return
     */
    private List<CourseListVO> searchTeacherName(List<Course> courseList) {
        Set<Integer> teacherIdList = courseList.stream().map(Course::getTeacherId).collect(Collectors.toSet());
        List<Integer> idList = new ArrayList<>(teacherIdList.size() + 1);
        idList.addAll(teacherIdList);
        List<User> userList = userInfoService.searchUserList(idList);
        Map<Integer, String> teacherNameMap = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<CourseListVO> result = new ArrayList<>(courseList.size() + 1);
        for (Course course : courseList) {
            CourseListVO courseListVO = DozerUtils.map(course, CourseListVO.class);
            courseListVO.setTeacherName(teacherNameMap.containsKey(course.getTeacherId()) ?
                    teacherNameMap.get(course.getTeacherId()) : "");
            result.add(courseListVO);
        }
        return result;
    }

    /**
     * 根据课程id批量查询课程
     *
     * @param courseIdList
     * @return
     */
    private List<Course> selectCourseByIdList(List<Integer> courseIdList, String name) {
        Search search = new Search();
        search.put("id_in", courseIdList);
        if (!StringUtils.isEmpty(name)) {
            search.put("name_like", name);
        }
        Example example = new ExampleBuilder(Course.class).search(search).build();
        List<Course> courseList = courseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(courseIdList)) {
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

    @Override
    public List<ClassManageVO> searchClassList(Integer id) {
        if (id == null || id <= 0) {
            return new ArrayList<>(0);
        }
        return searchClassAndStudent(id);
    }

    @Override
    public List<FileBaseVO> searchExperiment(Integer courseId, Integer classId) {
        return null;
    }

    @Override
    public EntitySaveVO updateClass(CourseDTO.UpdateClassDTO updateClassDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO(ResultCode.SUCCESS);
        if (updateClassDTO == null) {
            entitySaveVO.setResult(0, "参数为空");
            return entitySaveVO;
        }
        if (updateClassDTO.getId() == null || updateClassDTO.getId() <= 0) {
            return insertClass(updateClassDTO, entitySaveVO);
        }
        return updateClassById(updateClassDTO, entitySaveVO);
    }

    /**
     * 更新班级
     *
     * @param updateClassDTO
     * @param entitySaveVO
     * @return
     */
    private EntitySaveVO updateClassById(CourseDTO.UpdateClassDTO updateClassDTO, EntitySaveVO entitySaveVO) {
        Class myClass = DozerUtils.map(updateClassDTO, Class.class);
        try {
            classMapper.updateByPrimaryKey(myClass);
        } catch (Exception e) {
            log.warn("更新班级出错，req:{},e:{}", updateClassDTO, e);
            entitySaveVO.setResult(ResultCode.FAIL);
        }
        return entitySaveVO;
    }

    /**
     * 新增班级
     *
     * @param updateClassDTO
     * @param entitySaveVO
     * @return
     */
    private EntitySaveVO insertClass(CourseDTO.UpdateClassDTO updateClassDTO, EntitySaveVO entitySaveVO) {
        Class myClass = DozerUtils.map(updateClassDTO, Class.class);
        try {
            classMapper.insertSelective(myClass);
        } catch (Exception e) {
            log.warn("新增班级出错，req:{},e:{}", updateClassDTO, e);
            entitySaveVO.setResult(ResultCode.FAIL);
        }
        return entitySaveVO;
    }

    @Override
    public EntitySaveVO updateStudent(CourseDTO.UpdateStudentDTO updateStudentDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO(ResultCode.SUCCESS);
        if (updateStudentDTO == null) {
            entitySaveVO.setResult(0, "参数为空");
            return entitySaveVO;
        }
        if (updateStudentDTO.getId() == null || updateStudentDTO.getId() <= 0) {
            return insertStudent(updateStudentDTO, entitySaveVO);
        }
        return updateStudentById(updateStudentDTO, entitySaveVO);
    }

    @Override
    public List<CourseListVO> searchCourse(CourseDTO.SearchCourseDTO searchCourseDTO) {
        if (searchCourseDTO == null || StringUtils.isEmpty(searchCourseDTO.getName())) {
            return getAllCourse(AuthorityUtils.getUserId());
        }
        List<CourseListVO> courseListVOList = searchCourseByCourseName(searchCourseDTO.getName());

        courseListVOList.addAll(searchCourseByTeacherName(searchCourseDTO.getName()));
        log.info("根据名称搜索课程成功，result：{}", courseListVOList);
        List<CourseListVO> result = courseListVOList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CourseListVO::getId))),
                courseListVOs -> new ArrayList<>(courseListVOs)
        ));
        searchFileBase(result);
        log.info("根据名称搜索课程成功,去重，返回搜索结果，result:{}", result);
        return result;
    }

    @Override
    public EntitySaveVO applyCourse(CourseDTO.ApplyCourseDTO applyCourseDTO) {
        Integer userId = AuthorityUtils.getUserId();
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setResult(ResultCode.FAIL);
        if (applyCourseDTO == null || applyCourseDTO.getCourseId() == null || userId == null || userId <= 0) {
            log.info("申请加入课程参数不正确");
            return entitySaveVO;
        }
        if(checkApplyTimes(userId,applyCourseDTO.getCourseId())){
            entitySaveVO.setMessage("请勿重复申请");
            log.warn("{} 重复申请课程{}",userId,applyCourseDTO.getCourseId());
            return entitySaveVO;
        }
        CourseUser courseUser = new CourseUser();
        courseUser.setCourseId(applyCourseDTO.getCourseId());
        courseUser.setUserId(userId);
        courseUser.setStatus(0);
        courseUser.setIsCreate(0);
        int time = DateUtils.currentTimeSeconds();
        courseUser.setCreateTime(time);
        courseUser.setUpdateTime(time);
        int result = courseUserMapper.insertSelective(courseUser);
        if (result <= 0) {
            log.info("{}申请加入课程失败，{}", userId, applyCourseDTO);
            return entitySaveVO;
        }
        log.info("{}申请加入课程成功，{}", userId, courseUser);
        entitySaveVO.setResult(ResultCode.SUCCESS);
        return entitySaveVO;
    }

    /**
     * 检查是否重复申请
     * @param userId
     * @param courseId
     * @return
     */
    private Boolean checkApplyTimes(Integer userId,Integer courseId) {
        Search search = new Search();
        search.put("userId_eq",userId);
        search.put("courseId_eq",courseId);
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courseUserList)){
            return true;
        }
        return false;
    }

    @Override
    public EntitySaveVO dealApply(CourseDTO.DealCourseDTO dealCourseDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setResult(ResultCode.FAIL);
        if (dealCourseDTO == null || dealCourseDTO.getId() == null || dealCourseDTO.getStatus() == null) {
            log.info("处理课程参数不正确，{}", dealCourseDTO);
            return entitySaveVO;
        }
        CourseUser courseUser = new CourseUser();
        courseUser.setId(dealCourseDTO.getId());
        courseUser.setStatus(dealCourseDTO.getStatus());
        int result = courseUserMapper.updateByPrimaryKeySelective(courseUser);
        if (result <= 0) {
            log.info("处理课程参数不正确");
            return entitySaveVO;
        }
        log.info("处理课程成功，{}", dealCourseDTO);
        entitySaveVO.setResult(ResultCode.SUCCESS);
        return entitySaveVO;
    }

    @Override
    public List<ApplyMessageVO> searchApplyMessage(ApplyDTO.SearchApplyMessageDTO req) {
        if (req == null || req.getCourseId() == null) {
            return new ArrayList<>(0);
        }
        Search search = new Search();
        search.put("status_eq", 0);
        search.put("courseId_eq", req.getCourseId());
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        log.info("查询申请信息成功，result:{}", courseUserList);
        List<ApplyMessageVO> result = DozerUtils.mapList(courseUserList, ApplyMessageVO.class);
        searchNameAndPhone(result);
        return result;
    }

    /**
     * 根据userId获取名称和联系方式
     *
     * @param result
     */
    private void searchNameAndPhone(List<ApplyMessageVO> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        List<Integer> userIdList = result.stream().map(ApplyMessageVO::getUserId).collect(Collectors.toList());
        List<User> userList = userInfoService.searchUserList(userIdList);
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user, (newData, oldData) -> newData));
        for(ApplyMessageVO applyMessageVO : result){
            User user = userMap.get(applyMessageVO.getUserId());
            if(user == null){
                continue;
            }
            applyMessageVO.setName(user.getName());
            applyMessageVO.setPhone(user.getPhone());
        }
    }

    /**
     * 根据教师名称搜索课程
     *
     * @param name
     * @return
     */
    private List<CourseListVO> searchCourseByTeacherName(String name) {
        List<UserVO> userVOList = userInfoService.searchUserByName(name);
        if (CollectionUtils.isEmpty(userVOList)) {
            return new ArrayList<>();
        }
        List<Integer> userIdList = userVOList.stream().map(UserVO::getId).collect(Collectors.toList());
        List<Course> courseList = getCourseByUserIdList(userIdList);
        if (CollectionUtils.isEmpty(courseList)) {
            return new ArrayList<>();
        }
        log.info("根据教师名称搜索课程成功，name:{}， result:{}", name, courseList);
        return searchTeacherName(courseList);
    }

    /**
     * 根据教师id查询课程
     *
     * @param userIdList
     * @return
     */
    private List<Course> getCourseByUserIdList(List<Integer> userIdList) {
        Search search = new Search();
        search.put("teacherId_in", userIdList);
        Example example = new ExampleBuilder(Course.class).search(search).build();
        return courseMapper.selectByExample(example);
    }

    /**
     * 根据课程名称搜索课程
     *
     * @param name
     * @return
     */
    private List<CourseListVO> searchCourseByCourseName(String name) {
        List<Course> courseList = getCourseByName(name);
        if (CollectionUtils.isEmpty(courseList)) {
            return new ArrayList<>();
        }
        log.info("根据课程名称搜索课程成功，name:{}， result:{}", name, courseList);
        return searchTeacherName(courseList);
    }

    /**
     * 根据名称查询课程
     *
     * @param name
     * @return
     */
    private List<Course> getCourseByName(String name) {
        Search search = new Search();
        search.put("name_like", name);
        Example example = new ExampleBuilder(Course.class).search(search).build();
        return courseMapper.selectByExample(example);
    }

    /**
     * 更新学生
     *
     * @param updateStudentDTO
     * @param entitySaveVO
     * @return
     */
    private EntitySaveVO updateStudentById(CourseDTO.UpdateStudentDTO updateStudentDTO, EntitySaveVO entitySaveVO) {
        CourseUser courseUser = DozerUtils.map(updateStudentDTO, CourseUser.class);
        try {
            courseUserMapper.updateByPrimaryKey(courseUser);
        } catch (Exception e) {
            log.warn("更新学生出错，req:{},e:{}", updateStudentDTO, e);
            entitySaveVO.setResult(ResultCode.FAIL);
        }
        return entitySaveVO;
    }

    /**
     * 新增学生
     *
     * @param updateStudentDTO
     * @param entitySaveVO
     * @return
     */
    private EntitySaveVO insertStudent(CourseDTO.UpdateStudentDTO updateStudentDTO, EntitySaveVO entitySaveVO) {
        CourseUser courseUser = DozerUtils.map(updateStudentDTO, CourseUser.class);
        try {
            courseUserMapper.insertSelective(courseUser);
        } catch (Exception e) {
            log.warn("新增学生出错，req:{},e:{}", updateStudentDTO, e);
            entitySaveVO.setResult(ResultCode.FAIL);
        }
        return entitySaveVO;
    }

    /**
     * 获取课程详情
     *
     * @param id
     * @return
     */
    private CourseInfoVO searchCourseById(Integer id) {
        //课程信息
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null) {
            return new CourseInfoVO();
        }
        CourseInfoVO result = DozerUtils.map(course, CourseInfoVO.class);
        //获取教师名称
        result.setTeacherName(getUserNameById(course.getTeacherId()));
        //班级信息 todo
        //实验信息
        //动态 todo
        //统计信息 todo
        //

        return result;
    }

    /**
     * 根据用户id获取名称
     *
     * @param teacherId
     * @return
     */
    private String getUserNameById(Integer teacherId) {
        UserVO userVO = userInfoService.searchUserNameById(teacherId);
        if (userVO == null || StringUtils.isEmpty(userVO.getName())) {
            return "教师";
        }
        return userVO.getName();
    }

    /**
     * 根据课程id获取对应班级和学生
     *
     * @param courseId
     * @return List<ClassManageVO>
     */
    private List<ClassManageVO> searchClassAndStudent(Integer courseId) {
        List<ClassManageVO> result = new ArrayList<>(4);
        // 获取默认班级学生 todo
        result.add(searchClassAndStudent(courseId, 0));
        // 获取其余班级、学生
        result.addAll(searchClassAndStudentList(courseId));
        return result;
    }

    /**
     * 查询课程下所有班级，以及班级的学生
     *
     * @param courseId
     * @return
     */
    private Collection<? extends ClassManageVO> searchClassAndStudentList(Integer courseId) {
        List<ClassManageVO> result = new ArrayList<>(4);
        List<Class> classList = searchClassByCourseId(courseId);
        for (Class myClass : classList) {
            result.add(searchClassAndStudent(courseId, myClass.getId(), myClass.getName()));
        }
        return result;
    }


    /**
     * 根据课程id获取班级
     *
     * @param courseId
     * @return
     */
    private List<Class> searchClassByCourseId(Integer courseId) {
        Search search = new Search();
        search.put("courseId_eq", courseId);
        Example example = new ExampleBuilder(Class.class).search(search).build();
        List<Class> classList = classMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(classList)) {
            return new ArrayList<>();
        }
        return classList;
    }


    /**
     * 获取课程、班级、学生
     *
     * @param courseId
     * @param classId
     * @param name
     * @return
     */
    private ClassManageVO searchClassAndStudent(Integer courseId, Integer classId, String name) {
        ClassManageVO classManageVO = searchClassAndStudent(courseId, classId);
        classManageVO.setId(classId);
        classManageVO.setName(name);
        classManageVO.setCourseId(courseId);
        return classManageVO;
    }

    /**
     * 获取课程班级以及学生
     *
     * @param courseId
     * @return
     */
    private ClassManageVO searchClassAndStudent(Integer courseId, Integer classId) {
        ClassManageVO result = new ClassManageVO();
        Search search = new Search();
        search.put("courseId_eq", courseId);
        search.put("classId_eq", classId);
        search.put("isCreate_eq", 0);
        search.put("status_eq", 1);
        Example example = new ExampleBuilder(CourseUser.class).search(search).build();
        List<CourseUser> courseUserList = courseUserMapper.selectByExample(example);
        List<Integer> userIdList = courseUserList.stream().map(CourseUser::getUserId).collect(Collectors.toList());
        List<UserListVO> userListVOList = DozerUtils.mapList(userInfoService.searchUserList(userIdList), UserListVO.class);
        log.info("查询班级学生成功，userList:{}", userListVOList);
        if (classId == null || classId <= 0) {
            result.setDefaultCourse(0, courseId, "默认班级", 0, userListVOList);
            return result;
        }
        result.setUserListVOList(userListVOList);
        return result;
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
        courseUser.setStatus(1);
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
