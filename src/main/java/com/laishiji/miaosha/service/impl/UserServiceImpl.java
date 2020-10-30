package com.laishiji.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.laishiji.miaosha.dao.UserDOMapper;
import com.laishiji.miaosha.dao.UserPasswordDOMapper;
import com.laishiji.miaosha.dataobject.UserDO;
import com.laishiji.miaosha.dataobject.UserPasswordDO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.service.UserService;
import com.laishiji.miaosha.service.model.UserModel;
import com.laishiji.miaosha.validator.ValidationResult;
import com.laishiji.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userDOMapper")
    private UserDOMapper userDOMapper;

    @Resource(name = "userPasswordDOMapper")
    private UserPasswordDOMapper userPasswordDOMapper;

    @Resource(name = "validator")
    private ValidatorImpl validator;

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null) return null;
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);
    }

    /**
     * 用户注册
     * @param userModel
     * @throws BusinessException
     */
    @Override
    @Transactional//实现事务
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);

        //参数校验
        ValidationResult result =  validator.validate(userModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());

        //实现model -> dataobject
        UserDO userDO = convertFromModel(userModel);

        try{
            //设置为自增长id后，数据库会进行映射，调用该方法后userDO的id属性被赋值
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException e){
            //在数据库中将telphone字段设置为唯一索引，若重复则抛出DuplicateKeyException
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已经被注册");
        }

        //给userModel的id赋值，并将其关联给user_password表的user_id
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPassWordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    /**
     * 用户登录验证
     * @param telphone
     * @param encryptPassword
     * @throws BusinessException
     */
    @Override
    public UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException {
        //1.通过手机号获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null)
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        //2.校验密码是否匹配
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if(!StringUtils.equals(encryptPassword, userPasswordDO.getEncryptPassword())){
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        return userModel;
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

    /**
     * 将UserModel转换为UserDO，用于ORM
     * @param userModel
     * @return
     */
    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null) return null;

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);

        return userDO;
    }

    /**
     * 将UserModel转换为UserPasswordDO，用于ORM
     * @param userModel
     * @return
     */
    private UserPasswordDO convertPassWordFromModel(UserModel userModel){
        if(userModel == null) return null;

        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassword());
        userPasswordDO.setUserId(userModel.getId());

        return userPasswordDO;
    }

}
