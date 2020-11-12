package com.laishiji.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class PromoModel implements Serializable {
    private Integer id;

    //秒杀活动状态，为1表示还未开始，为2表示进行中，为3表示已结束
    private Integer status;

    //秒杀活动名称
    private String promoName;

    //秒杀开始时间
    private DateTime startDate;

    //秒杀结束时间
    private DateTime endDate;

    //秒杀活动的适用商品
    private Integer itemId;

    ////秒杀活动的商品价格
    private BigDecimal promoItemPrice;
}
