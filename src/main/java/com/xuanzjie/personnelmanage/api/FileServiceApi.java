package com.xuanzjie.personnelmanage.api;

import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "personnel-manage")
@Api(tags = "文件模块")
public interface FileServiceApi {
    /**
     * 更新文件
     *
     * @param fileBaseDTO
     * @return
     */
    @ApiOperation(value = "新增/更新文件")
    @PostMapping("/file/updateFileBase")
    ResResult<EntitySaveVO> updateFileBase(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO);
}
