package com.xuanzjie.personnelmanage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

public class CourseDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("搜索类型")
    public static class GetCourseListDTO {

        /**
         * 搜索类型
         * 0 || null 登录账号创建的课程， 1 登录账号加入的课程
         */
        @ApiModelProperty(notes = "搜索类型")
        private Integer searchType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateCourseDTO {

        /**
         * id
         */
        private Integer id;
        /**
         * 名称
         */
        private String name;

        /**
         * 说明
         */
        private String explanation;

        /**
         * 封面路径
         */
        private String cover;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetCourseDetailDTO{

        /**
         * 课程id
         */
        private Integer id;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetClassManageDTO{

        /**
         * 课程id
         */
        private Integer id;
    }
}
