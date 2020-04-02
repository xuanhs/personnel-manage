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

        @ApiModelProperty(notes = "课程名称")
        private String name;
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
        private Integer fileId;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "获取课程详情")
    public static class GetCourseDetailDTO{

        /**
         * 课程id
         */
        @ApiModelProperty(notes = "课程id")
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "UpdateClassDTO")
    public static class UpdateClassDTO{

        /**
         * 主键id
         */
        @ApiModelProperty(notes = "id")
        private Integer id;

        /**
         * 课程id
         */
        @ApiModelProperty(notes = "课程id")
        private Integer courseId;

        /**
         * 班级名称
         */
        @ApiModelProperty(notes = "班级名称")
        private String name;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "UpdateStudentDTO")
    public static class UpdateStudentDTO{

        /**
         * 主键id
         */
        @ApiModelProperty(notes = "id")
        private Integer id;

        /**
         * 课程id
         */
        @ApiModelProperty(notes = "课程id")
        private Integer courseId;

        /**
         * 班级id
         */
        @ApiModelProperty(notes = "班级id")
        private Integer classId;

        /**
         * 用户id
         */
        @ApiModelProperty(notes = "用户id")
        private Integer userId;

        /**
         * 状态 -1 删除、0 申请中 1加入
         */
        @ApiModelProperty(notes = "状态")
        private String status;

        /**
         * 学号
         */
        @ApiModelProperty(notes = "学号")
        private String code;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "SearchCourseDTO")
    public static class SearchCourseDTO{

        /**
         * 搜索类型 0 || null 课程维度，1 教师
         */
        @ApiModelProperty(notes = "搜索类型")
        private Integer type;

        @ApiModelProperty(notes = "名称")
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "ApplyCourseDTO")
    public static class ApplyCourseDTO{

        @ApiModelProperty(notes = "课程id")
        private Integer courseId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "DealCourseDTO")
    public static class DealCourseDTO{


        @ApiModelProperty(notes = "中间表主键id")
        private Integer id;

        @ApiModelProperty(notes = "课程id")
        private Integer courseId;

        @ApiModelProperty(notes = "用户id")
        private Integer userId;

        @ApiModelProperty(notes = "状态")
        private Integer status;

    }
}
