package com.laishiji.miaosha.service;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 获取商品列表
     * @return
     */
    List<ItemModel> listItem();

    /**
     * 通过id获取商品详情
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);
}
