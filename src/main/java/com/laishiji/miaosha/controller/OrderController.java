package com.laishiji.miaosha.controller;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.response.CommonReturnType;
import com.laishiji.miaosha.service.OrderService;
import com.laishiji.miaosha.service.model.OrderModel;
import com.laishiji.miaosha.service.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", origins = {"*"})//跨域请求
public class OrderController extends CommonController{

    @Resource(name="orderService")
    private OrderService orderService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId,
                                        HttpServletRequest request) throws BusinessException {
        //获取用户登录信息
        Boolean isLogin = (Boolean) request.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || isLogin.booleanValue() == false)
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN,"用户未登录，无法下单");

        UserModel userModel = (UserModel) request.getSession().getAttribute("LOGIN_USER");

        //创建订单
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);

        return CommonReturnType.create(null);
    }


}
