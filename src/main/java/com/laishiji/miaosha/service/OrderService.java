package com.laishiji.miaosha.service;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.service.model.OrderModel;

public interface OrderService {
    /**
     * 创建订单, 并且在订单中验证秒杀活动id是否属于对应商品并验证活动已开始
     * @param userId
     * @param itemId
     * @param amount
     * @return
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;



}
