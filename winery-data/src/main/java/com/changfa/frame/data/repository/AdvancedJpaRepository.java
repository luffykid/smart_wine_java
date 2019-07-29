package com.changfa.frame.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * JPA仓储类的统一接口，支持新增、修改、批量删除、分页查询及排序等操作。
 *
 * @param <T>  实体类型。
 * @param <ID> 主键类型。
 * @author WangRui
 */
@NoRepositoryBean
public interface AdvancedJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {
}
