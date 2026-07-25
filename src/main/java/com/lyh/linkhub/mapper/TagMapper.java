package com.lyh.linkhub.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper {
    Long selectIdByName(@Param("name") String name);

    int insertIfNotExists(@Param("name") String name);

    List<String> selectAll();
}
