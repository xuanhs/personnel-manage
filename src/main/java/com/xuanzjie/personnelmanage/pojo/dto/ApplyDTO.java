package com.xuanzjie.personnelmanage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApplyDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "SearchApplyMessageDTO")
    public static class SearchApplyMessageDTO{

        @ApiModelProperty(notes = "课程id")
        private Integer courseId;

    }

}
