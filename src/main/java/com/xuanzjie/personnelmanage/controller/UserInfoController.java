package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.UserInfoApi;
import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.UserInfoService;
import com.xuanzjie.personnelmanage.utils.ResResult;
import com.xuanzjie.personnelmanage.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController implements UserInfoApi {
    @Autowired
    UserInfoService userInfoService;

    @Override
    public ResResult<EntitySaveVO> register(@RequestBody @Validated UserDTO userDTO) {
        if (userDTO == null) {
            return ResUtils.data(new EntitySaveVO(ResultCode.INPUT_EXCEPTION.getCode(),ResultCode.INPUT_EXCEPTION.getMessage()));
        }
        return ResUtils.data(userInfoService.register(userDTO));
    }

    @Override
    public ResResult<EntitySaveVO> login(@RequestBody @Validated UserDTO userDTO) {
        if (userDTO == null) {
            return ResUtils.data(new EntitySaveVO(ResultCode.INPUT_EXCEPTION.getCode(),ResultCode.INPUT_EXCEPTION.getMessage()));
        }
        return ResUtils.data(userInfoService.login(userDTO));
    }

    @Override
    public ResResult<String> test() {
        return ResUtils.data("success");
    }
}
