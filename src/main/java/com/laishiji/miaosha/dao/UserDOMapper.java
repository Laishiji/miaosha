package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.UserDO;
import org.springframework.stereotype.Repository;

@Repository("userDOMapper")
public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}