package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {

    ResultCode register(UserDTO userDTO);

    ResultCode login(UserDTO userDTO);
}
