package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.PromoDO;
import org.springframework.stereotype.Repository;

@Repository("promoDOMapper")
public interface PromoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer id);

    /**
     * 根据item_id获取活动信息
     * @param itemId
     * @return
     */
    PromoDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}