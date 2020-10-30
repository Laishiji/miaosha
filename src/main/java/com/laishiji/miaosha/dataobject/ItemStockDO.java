package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemStockDO {
    private Integer id;

    private Integer stock;

    private Integer itemId;
}