package com.laishiji.miaosha.dataobject;

import lombok.Getter;
import lombok.Setter;

/**
 * 该类用于生成订单号的中间6位的自增序列
 */
@Setter
@Getter
public class SequenceDO {
    private String name;//主键

    //当前序列值，当currentValue超过6位怎么办？
    //应该在数据库中设置一个最大值，当currentValue达到最大值后，将其重新设置为0，循环使用
    private Integer currentValue;

    private Integer step;//步长：每次下单后给currentValue增加的值
}