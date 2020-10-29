package com.laishiji.miaosha.dao;

import com.laishiji.miaosha.dataobject.UserPasswordDO;
import org.springframework.stereotype.Repository;

@Repository("userPasswordDOMapper")
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByPrimaryKey(Integer id);

    /**
     * 添加通过用户id查询密码的方法，而不是通过表的主键
     * @param userId
     * @return
     */
    UserPasswordDO selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);
}