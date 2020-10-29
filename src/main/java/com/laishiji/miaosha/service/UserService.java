package com.laishiji.miaosha.service;

import com.laishiji.miaosha.service.model.UserModel;

public interface UserService {
    /**
     * 通过用户id获取用户对象
     * @param id
     * @return usermodel
     */
    UserModel getUserById(Integer id);
}
