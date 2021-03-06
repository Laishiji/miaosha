package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.ItemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("itemDOMapper")
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    /**
     * 获取商品列表
     * @return
     */
    List<ItemDO> listItem();

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    /**
     * 更新销量
     * @param id
     * @param amount
     * @return
     */
    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
}