package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.UserInfoApi;
import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
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
    public ResResult<String> register(UserDTO userDTO) {
        if (userDTO == null) {
            return ResUtils.data(ResultCode.INPUT_EXCEPTION.getCode(), ResultCode.INPUT_EXCEPTION.getMessage());
        }
        ResultCode resultCode = userInfoService.register(userDTO);
        return ResUtils.data(resultCode.getCode(), resultCode.getMessage());
    }

    @Override
    public ResResult<String> login(@RequestBody @Validated UserDTO userDTO) {
        if (userDTO == null) {
            return ResUtils.data(ResultCode.INPUT_EXCEPTION.getCode(), ResultCode.INPUT_EXCEPTION.getMessage());
        }
        ResultCode resultCode = userInfoService.login(userDTO);
        return ResUtils.data(resultCode.getCode(), resultCode.getMessage());
    }
}
