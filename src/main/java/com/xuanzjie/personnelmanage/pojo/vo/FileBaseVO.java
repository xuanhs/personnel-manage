package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.Data;

@Data
public class FileBaseVO {

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

    private Integer createTime;

    private Integer updateTime;
}
