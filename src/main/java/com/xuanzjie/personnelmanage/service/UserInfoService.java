package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.pojo.dto.UserDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {

    EntitySaveVO register(UserDTO userDTO);

    EntitySaveVO login(UserDTO userDTO);
}
