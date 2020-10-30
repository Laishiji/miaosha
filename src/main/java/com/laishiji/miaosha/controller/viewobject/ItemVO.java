package com.laishiji.miaosha.controller.viewobject;

import lombok.Getter;
import lombok.Setter;

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
}
