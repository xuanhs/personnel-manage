package com.xuanzjie.personnelmanage.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Integer id;

    private String emile;

    private String cardId;

    private Integer identity;

    private Integer registerTime;

    private Integer loginTime;

    private String name;

    private String password;
}
