package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDO {
    private Integer id;

    private String title;

    //数据库中若设置成BigDecimal，会自动生成Long,因此数据库中该字段设置为Double类型
    private Double price;

    private String description;

    private Integer sales;

    private String imgUrl;
}