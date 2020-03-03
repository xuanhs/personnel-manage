package com.xuanzjie.personnelmanage.pojo.vo;

import com.xuanzjie.personnelmanage.comman.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntitySaveVO {

    private Integer code;

    private String message;

    public EntitySaveVO(ResultCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
