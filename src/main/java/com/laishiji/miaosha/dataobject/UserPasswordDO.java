package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

/**
 * 企业级应用中，用户密码绝不能与用户表放在同一张表中，最简单的做法是使用在数据库中新建一张表，
 * 存储加密后的密码串。
 */
@Getter
@Setter
public class UserPasswordDO {
    private Integer id;

    private String encryptPassword;

    private Integer userId;
}