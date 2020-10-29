package com.laishiji.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * SpringMVC中我们用于进行业务逻辑处理的Model概念，
 * datoobject中只是定义简单的pojo类，仅用于ORM
 */
@Getter
@Setter
public class UserModel {
    private Integer id;
    private String name;
    private Boolean gender;
    private Integer age;
    private String telphone;
    private String registerMode;
    private String thirdPartyId;

    //dataobject.UserDO中所没有encyptPassword
    private String encryptPassword;

}
