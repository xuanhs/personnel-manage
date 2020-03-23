package com.xuanzjie.personnelmanage.pojo.dto;

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
    public static class GetExperimentDTO{
        /**
         * 课程id
         */
       private Integer id;
    }
}
