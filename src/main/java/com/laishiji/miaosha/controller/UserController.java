package com.laishiji.miaosha.controller;

import com.laishiji.miaosha.controller.viewobject.UserVO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.response.CommonReturnType;
import com.laishiji.miaosha.service.UserService;
import com.laishiji.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController extends CommonController{

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //用户不存在则抛出用户不存在异常
        if(userModel == null) {
            //int i = 1/0; //测试未知错误
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST_ERROR);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO =  convertFromModel(userModel);
        //归一化处理，返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
