package com.xuanzjie.personnelmanage.pojo.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Data
@ToString
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "emile")
    private String emile;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "identity")
    private Integer identity;

    @Column(name = "register_time")
    private Integer registerTime;

    @Column(name = "login_time")
    private Integer loginTime;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
}
