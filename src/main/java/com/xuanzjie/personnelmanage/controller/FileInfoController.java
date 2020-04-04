package com.xuanzjie.personnelmanage.controller;

import com.xuanzjie.personnelmanage.api.FileServiceApi;
import com.xuanzjie.personnelmanage.pojo.dto.CourseDTO;
import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.service.FileService;
import com.xuanzjie.personnelmanage.utils.ResResult;
import com.xuanzjie.personnelmanage.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileInfoController implements FileServiceApi {

    @Autowired
    FileService fileService;

    @Override
    public ResResult<EntitySaveVO> updateFileBase(@RequestBody @Validated FileBaseDTO.UpdateFileBaseDTO fileBaseDTO) {
        return ResUtils.data(fileService.updateFileBase(fileBaseDTO));
    }
}
