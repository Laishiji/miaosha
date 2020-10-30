package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.ItemStockDO;
import org.springframework.stereotype.Repository;

@Repository("itemStockDOMapper")
public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    //通过item_id获取库存信息
    ItemStockDO selectByItemId(Integer itemId);
}