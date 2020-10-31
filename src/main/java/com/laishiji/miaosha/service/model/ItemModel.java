package com.laishiji.miaosha.service.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品模型
 */
@Setter
@Getter
public class ItemModel {
    private Integer id;

    //商品名
    @NotBlank(message = "商品名称不能为空")
    private String title;

    //价格，BigDecimal用于对超过16位的数进行精确运算，商业计算最好使用该类
    @NotNull(message = "商品价格必须填写")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    //库存量
    @NotNull(message = "库存必须填写")
    private Integer stock;

    //商品描述
    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    //销量
    private Integer sales;

    //商品描述图片的url
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

    //使用聚合模型，如果promoModel不为空，表示当前商品对象拥有还未结束的秒杀活动
    private PromoModel promoModel;

}
