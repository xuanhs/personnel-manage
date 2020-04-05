package com.xuanzjie.personnelmanage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ExperimentDTO {

    /**
     *
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("GetExperimentDTO")
    public static class GetExperimentDTO{
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
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel("UpdateFileBaseDTO")
    public static class UpdateExperimentDTO{

        /**
         * id
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
         * 到期时间
         */
        @ApiModelProperty(notes = "到期时间")
        private Integer deadline;

        /**
         * name
         */
        @ApiModelProperty(notes = "名称")
        private Integer name;
    }
}
