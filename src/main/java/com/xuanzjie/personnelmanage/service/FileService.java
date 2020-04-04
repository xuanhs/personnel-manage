package com.xuanzjie.personnelmanage.service;

import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import com.xuanzjie.personnelmanage.pojo.po.FileBase;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.pojo.vo.FileBaseVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FileService {

    /**
     * 根据id查询文件
     * @param ids
     * @return
     */
    List<FileBaseVO> searchFileListByIds(List<Integer> ids);

    /**
     * 更新文件
     * @param fileBaseDTO
     * @return
     */
    EntitySaveVO updateFileBase(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO);
}
