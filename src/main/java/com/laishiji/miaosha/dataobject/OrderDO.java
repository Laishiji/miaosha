package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;

    //增加秒杀活动id
    private Integer promoId;
}