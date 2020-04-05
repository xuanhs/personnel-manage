package com.xuanzjie.personnelmanage.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer id;

    private String emile;

    private String phone;

    private String cardId;

    private Integer identity;

    private Integer registerTime;

    private Integer loginTime;

    private String name;
}
