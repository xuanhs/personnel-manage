package com.xuanzjie.personnelmanage.api;

import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "personnel-manage")
@Api(tags = "用户信息接口")
public interface UserServiceApi {

    @PostMapping(value = "/register")
    ResResult<EntitySaveVO> register(UserDTO userDTO);

    @PostMapping(value = "/login")
    ResResult<EntitySaveVO> login(UserDTO userDTO);

    @GetMapping(value = "/test")
    ResResult<String> test();
}
