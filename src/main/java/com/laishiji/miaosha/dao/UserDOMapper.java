package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.UserDO;
import org.springframework.stereotype.Repository;

@Repository("userDOMapper")
public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    //添加通过手机号查询用户信息
    UserDO selectByTelphone(String telphone);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}