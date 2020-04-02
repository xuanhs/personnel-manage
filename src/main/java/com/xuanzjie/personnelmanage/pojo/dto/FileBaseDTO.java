package com.xuanzjie.personnelmanage.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FileBaseDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("新增/更新文件")
    public static class UpdateFileBaseDTO{
        private Integer id;

        /**
         * 原文件名
         */
        private String fileName;

        /**
         * 文件路径
         */
        private String filePath;

        /**
         * 存储文件名
         */
        private String memoryName;
    }
}
