package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("itemStockDOMapper")
public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 通过item_id更新库存
     * @param itemId
     * @param amount
     * @return 影响的行数
     */
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    //通过item_id获取库存信息
    ItemStockDO selectByItemId(Integer itemId);
}