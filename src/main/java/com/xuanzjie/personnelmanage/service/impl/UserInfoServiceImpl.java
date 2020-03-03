package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.mapper.UserMapper;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.pojo.po.User;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.UserInfoService;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import com.xuanzjie.personnelmanage.utils.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

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


    //昵称查重
    private boolean checkUserName(UserDTO userDTO) {
        Search search = new Search();
        search.put("name", userDTO.getName());
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
        if(StringUtils.isEmpty(userDTO)){
            return false;
        }
        User inputUser = DozerUtils.map(userDTO, User.class);
        inputUser.setPassword(null);
        List<User> userList = userMapper.select(inputUser);

        if(CollectionUtils.isEmpty(userList)){
            return false;
        }
        return true;
    }
}
