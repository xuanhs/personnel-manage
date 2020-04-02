package com.xuanzjie.personnelmanage.service.impl;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import com.xuanzjie.personnelmanage.mapper.FileBaseMapper;
import com.xuanzjie.personnelmanage.pojo.dto.FileBaseDTO;
import com.xuanzjie.personnelmanage.pojo.po.FileBase;
import com.xuanzjie.personnelmanage.pojo.vo.EntitySaveVO;
import com.xuanzjie.personnelmanage.pojo.vo.FileBaseVO;
import com.xuanzjie.personnelmanage.search.ExampleBuilder;
import com.xuanzjie.personnelmanage.search.Search;
import com.xuanzjie.personnelmanage.service.FileService;
import com.xuanzjie.personnelmanage.utils.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    FileBaseMapper fileBaseMapper;

    @Override
    public List<FileBaseVO> searchFileListByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.warn("根据id查询文件传参为空");
            return null;
        }
        Search search = new Search();
        search.put("id_in", ids);
        Example example = new ExampleBuilder(FileBase.class).search(search).build();
        List<FileBase> fileBaseList =  fileBaseMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(fileBaseList)){
            log.warn("根据id查询文件结果为空，id{}",ids);
            return null;
        }
        log.info("根据id查询文件成功，id:{}}",ids);
        return DozerUtils.mapList(fileBaseList,FileBaseVO.class);
    }

    @Override
    public EntitySaveVO updateFileBase(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setResult(ResultCode.FAIL);
        if (fileBaseDTO == null || StringUtils.isEmpty(fileBaseDTO.getFileName())
                || StringUtils.isEmpty(fileBaseDTO.getMemoryName())) {
            log.warn("保存文件参数错误,{}", fileBaseDTO);
            return entitySaveVO;
        }
        if (fileBaseDTO.getId() == null || fileBaseDTO.getId() <= 0) {
            return insertFile(fileBaseDTO);
        }
        return updateFile(fileBaseDTO);
    }

    /**
     * @param fileBaseDTO
     * @return
     */
    private EntitySaveVO updateFile(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setResult(ResultCode.FAIL);
        if (fileBaseDTO == null || StringUtils.isEmpty(fileBaseDTO.getFileName())
                || StringUtils.isEmpty(fileBaseDTO.getMemoryName())) {
            log.warn("更新文件参数出错,{}", fileBaseDTO);
            return entitySaveVO;
        }
        FileBase fileBase = DozerUtils.map(fileBaseDTO, FileBase.class);
        try {
            Integer data = fileBaseMapper.updateByPrimaryKeySelective(fileBase);
            if (data > 0) {
                entitySaveVO.setSucessKey(fileBase.getId());
            }
            return entitySaveVO;
        } catch (Exception e) {
            log.warn("更新文件出错，e:{}", e);
            return entitySaveVO;
        }

    }

    private EntitySaveVO insertFile(FileBaseDTO.UpdateFileBaseDTO fileBaseDTO) {
        EntitySaveVO entitySaveVO = new EntitySaveVO();
        entitySaveVO.setResult(ResultCode.FAIL);
        if (fileBaseDTO == null || StringUtils.isEmpty(fileBaseDTO.getFileName())
                || StringUtils.isEmpty(fileBaseDTO.getMemoryName())) {
            log.warn("保存文件参数错误,{}", fileBaseDTO);
            return entitySaveVO;
        }
        FileBase fileBase = DozerUtils.map(fileBaseDTO, FileBase.class);
        try {
            Integer data = fileBaseMapper.insertSelective(fileBase);
            if (data > 0) {
                entitySaveVO.setSucessKey(fileBase.getId());
            }
            return entitySaveVO;
        } catch (Exception e) {
            log.warn("新增文件出错，e:{}", e);
            return entitySaveVO;
        }
    }
}
