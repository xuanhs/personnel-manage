package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import com.xuanzjie.personnelmanage.pojo.po.FileBase;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;

import java.util.Collection;
import java.util.List;

public interface FileService {

    /**
     * 根据id查询文件
     * @param ids
     * @return
     */
    List<FileBase> searchFileListByIds(Collection<Integer> ids);

    EntitySaveVO updateFileBase(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO);
}
