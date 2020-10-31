package com.laishiji.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户下单的交易模型
 */
@Setter
@Getter
public class OrderModel {

    //订单id
    private String id;

    //下单的用户id
    private Integer userId;

    //商品id
    private Integer itemId;

    //商品单价，若promoId非空，则表示以秒杀商品价格
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;

    //购买金额，若promoId非空，则表示以秒杀商品价格
    private BigDecimal orderPrice;

    //若非空则表示该订单以秒杀方式下单
    private Integer promoId;

}
