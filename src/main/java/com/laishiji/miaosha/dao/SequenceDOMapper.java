package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.SequenceDO;
import org.springframework.stereotype.Repository;

@Repository("sequenceDOMapper")
public interface SequenceDOMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO selectByPrimaryKey(String name);

    SequenceDO getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}