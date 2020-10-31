package com.laishiji.miaosha.service;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.service.model.OrderModel;

public interface OrderService {
    /**
     * 创建订单
     * @param userId
     * @param itemId
     * @param amount
     * @return
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;

}
