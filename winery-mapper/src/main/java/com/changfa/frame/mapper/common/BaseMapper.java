package com.changfa.frame.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * mapper 父接口
 */
public interface BaseMapper<T, ID extends Serializable> extends Serializable {

    int delete(@Param("id") ID id);

    int save(T entity);

    int update(T entity);

    T getById(@Param("id") ID id);

    List<T> selectList(T entity);
}
