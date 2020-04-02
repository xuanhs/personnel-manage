package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.mapper.UserMapper;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.pojo.po.User;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.pojo.vo.UserListVO;
import com.xuanzjie.personnelmanage.pojo.vo.UserVO;
import com.xuanzjie.personnelmanage.search.ExampleBuilder;
import com.xuanzjie.personnelmanage.search.Search;
import com.xuanzjie.personnelmanage.service.UserInfoService;
import com.xuanzjie.personnelmanage.utils.AuthorityUtils;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserMapper userMapper;

    @Override
    public EntitySaveVO register(UserDTO userDTO) {
        if (!checkUserName(userDTO)) {
            return new EntitySaveVO(ResultCode.NAME_REPEAT);
        }
        saveUser(userDTO);
        return new EntitySaveVO(ResultCode.SUCCESS);
    }


    @Override
    public EntitySaveVO login(UserDTO userDTO) {
        if (!checkUserPassword(userDTO)) {
            return new EntitySaveVO(ResultCode.PASSWORD_FAIL);
        }
        return new EntitySaveVO(ResultCode.SUCCESS);
    }

    @Override
    public List<User> searchUserList(List<Integer> idList) {
        Search search = new Search();
        search.put("id_in",idList);
        Example example = new ExampleBuilder(User.class).search(search).build();
        return userMapper.selectByExample(example);
    }

    @Override
    public List<UserVO> searchUserByName(String name) {
        Search search = new Search();
        search.put("identity_eq",1);
        search.put("name_like",name);
        Example example = new ExampleBuilder(User.class).search(search).build();
        List<User> userList  = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)){
            return new ArrayList<>(0);
        }
        return DozerUtils.mapList(userList,UserVO.class);
    }

    @Override
    public UserVO searchUserNameById(Integer id) {
        Search search = new Search();
        search.put("id_eq",id);
        Example example  = new ExampleBuilder(User.class).search(search).build();
        List<User> user = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(user)){
            log.info("根据id查询user为空，id{}",id);
            return new UserVO();
        }
        List<UserVO> userVOList = DozerUtils.mapList(user,UserVO.class);
        return userVOList.get(0);
    }


    //昵称查重
    private boolean checkUserName(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        List<User> userList = userMapper.select(user);

        if (CollectionUtils.isEmpty(userList)) {
            return true;
        }
        return false;
    }

    //保存用户信息
    private void saveUser(UserDTO userDTO) {
        User user = DozerUtils.map(userDTO, User.class);
        userMapper.insert(user);
    }

    //用户登录认证
    private Boolean checkUserPassword(UserDTO userDTO) {
        if(userDTO == null || StringUtils.isEmpty(userDTO.getName()) || StringUtils.isEmpty(userDTO.getPassword())){
            return false;
        }
       Search search = new Search();
        search.put("name_eq",userDTO.getName());
        Example example = new ExampleBuilder(User.class).search(search).build();
        List<User> userList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)){
            return false;
        }
        for(User user : userList){
            if(userDTO.getPassword().equals(user.getPassword())){
                AuthorityUtils.setUserId(user.getId());
                log.info("登录成功，user:{}",userDTO);
                return true;
            }
        }
        return false;
    }
}
