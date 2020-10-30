package com.laishiji.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * SpringMVC中我们用于进行业务逻辑处理的Model概念，
 * datoobject中只是定义简单的pojo类，仅用于ORM
 */
@Setter
@Getter
public class UserModel {
    private Integer id;

    @NotBlank(message = "用户名不能为空字符串")
    private String name;

    @NotNull(message = "性别不能不填写")
    private Byte gender;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Integer age;

    @NotBlank(message = "手机号不能为空字符串")
    private String telphone;

    private String registerMode;
    private String thirdPartyId;

    //dataobject.UserDO中所没有encyptPassword
    @NotBlank(message = "密码不能为空字符串")
    private String encryptPassword;
}
