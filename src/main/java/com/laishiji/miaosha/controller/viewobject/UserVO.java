package com.laishiji.miaosha.controller.viewobject;

import lombok.Getter;
import lombok.Setter;

/**
 * 简单的pojo类，用于返回给前端的用户对象，只返回用户对象的部分属性
 */
@Setter
@Getter
public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;
}
