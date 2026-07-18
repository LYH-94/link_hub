package com.lyh.linkhub.mapper;

import org.apache.ibatis.annotations.Param;

import com.lyh.linkhub.pojo.TUser;

public interface UserMapper {
    public TUser getUserById(@Param("id") Long id);
}
