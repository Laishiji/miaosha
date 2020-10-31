package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

@Repository("orderDOMapper")
public interface OrderDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}