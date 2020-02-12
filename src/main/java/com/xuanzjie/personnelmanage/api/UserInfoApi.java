package com.xuanzjie.personnelmanage.api;

import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "personnel-manage")
public interface UserInfoApi {

    @PostMapping(value = "/user/register")
    ResResult<String> register(UserDTO userDTO);

    @PostMapping(value = "/user/login")
    ResResult<String> login(UserDTO userDTO);
}
