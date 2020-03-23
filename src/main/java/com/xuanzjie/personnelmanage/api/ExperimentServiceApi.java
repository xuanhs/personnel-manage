package com.xuanzjie.personnelmanage.api;

import com.xuanzjie.personnelmanage.pojo.dto.ExperimentDTO;
import com.xuanzjie.personnelmanage.pojo.vo.ExperimentListVO;
import com.xuanzjie.personnelmanage.utils.ResResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "personnel-manage")
public interface ExperimentServiceApi {
    /**
     * 实验管理界面，获取实验
     */
    @PostMapping("/experiment/getExperiment")
    ResResult<ExperimentListVO> getExperiment(ExperimentDTO.GetExperimentDTO getExperimentDTO);
}
