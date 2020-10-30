package com.laishiji.miaosha.service;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.service.model.UserModel;

public interface UserService {
    /**
     * 通过用户id获取用户对象
     * @param id
     * @return usermodel
     */
    UserModel getUserById(Integer id);

    /**
     * 注册
     * @param userModel
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 登录验证接口,密码为加密后的
     * @param telphone
     * @param encryptPassword
     */
    UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException;
}
