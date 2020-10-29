package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

/**
 * 定义简单的pojo类，仅用于ORM
 */
@Getter
@Setter
public class UserDO {
    private Integer id;

    private String name;

    private Boolean gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;
}