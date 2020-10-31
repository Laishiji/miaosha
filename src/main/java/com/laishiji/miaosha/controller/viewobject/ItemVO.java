package com.laishiji.miaosha.controller.viewobject;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemVO {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;

    //秒杀活动状态，为0表示没有活动，为1表示还未开始，为2表示进行中
    private Integer promoStatus;

    //秒杀活动价格
    private BigDecimal promoPrice;

    //秒杀活动id
    private Integer promoId;

    //秒杀活动开始时间，用于倒计时展示
    private String startDate;
}
