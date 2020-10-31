package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PromoDO {
    private Integer id;

    private String promoName;

    private Date startDate;

    private Date endDate;

    private Integer itemId;

    private Double promoItemPrice;

}