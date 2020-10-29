package com.laishiji.miaosha.service.impl;

import com.laishiji.miaosha.dao.UserDOMapper;
import com.laishiji.miaosha.dao.UserPasswordDOMapper;
import com.laishiji.miaosha.dataobject.UserDO;
import com.laishiji.miaosha.dataobject.UserPasswordDO;
import com.laishiji.miaosha.service.UserService;
import com.laishiji.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDOMapper")
    private UserDOMapper userDOMapper;

    @Resource(name = "userPasswordDOMapper")
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null) return null;
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);
    }

    /**
     * 将userDO与userPasswordDO组装为userModel
     * @param userDO
     * @param userPasswordDO
     * @return userModel
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null) return null;
        UserModel userModel = new UserModel();
        //将对应userDO中的属性值复制给userModel，该方法必须保证变量类型与变量名都一致
        BeanUtils.copyProperties(userDO, userModel);

        if(userPasswordDO != null){
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }

        return  userModel;
    }

}
