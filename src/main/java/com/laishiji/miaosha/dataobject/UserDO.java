package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 定义简单的pojo类，仅用于ORM
 */
@Getter
@Setter
@ToString
public class UserDO {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;
}