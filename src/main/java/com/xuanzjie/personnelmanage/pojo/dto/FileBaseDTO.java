package com.xuanzjie.personnelmanage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FileBaseDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("UpdateFileBaseDTO")
    public static class UpdateFileBaseDTO{

        @ApiModelProperty(notes = "文件id")
        private Integer id;

        /**
         * 原文件名
         */
        @ApiModelProperty(notes = "原文件名")
        private String fileName;

        /**
         * 文件路径
         */
        @ApiModelProperty(notes = "文件路径")
        private String filePath;

        /**
         * 存储文件名
         */
        @ApiModelProperty(notes = "保存文件名")
        private String memoryName;
    }
}
