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

    //价格
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

}
