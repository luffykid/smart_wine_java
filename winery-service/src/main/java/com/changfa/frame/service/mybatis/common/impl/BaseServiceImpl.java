package com.changfa.frame.service.mybatis.common.impl;

import com.changfa.frame.mapper.common.BaseMapper;

import com.changfa.frame.service.mybatis.common.BaseService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * service 基类
 */
@SuppressWarnings({"unchecked"})
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected BaseMapper<T, ID> baseMapper;

    public int delete(ID id) {
        if (id == null) {
            return 0;
        }
        return baseMapper.delete(id);
    }

    public T save(T entity) {
        if (entity == null) {
            return null;
        }
        //id为字符串类型时需要设置默认值，否则认为id是数据库自动生成
        try {
            Class<?> idType = PropertyUtils.getPropertyType(entity, "id");
            if (idType == Long.class && PropertyUtils.getProperty(entity, "id") == null) {
                setPropertyValue(entity, "id", IDUtil.getId());
            }

            // 判断是否存在创建时间、修改时间两个字段，如果存在就设置值
            PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity);
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (descriptor.getName().equals("createDate")) {
                    setPropertyValue(entity, "createDate", new Date());
                } else if (descriptor.getName().equals("modifyDate")) {
                    setPropertyValue(entity, "modifyDate", new Date());
                }
            }
        } catch (Exception e) {
            log.info("pojo类：" + entity.getClass() + "无id属性");
        }
        return baseMapper.save(entity) > 0 ? entity : null;
    }

    public T update(T entity) {
        if (entity == null) {
            return null;
        }
        // 判断是否存在修改时间字段，如果存在就更新为当前值
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getName().equals("modifyDate")) {
                setPropertyValue(entity, "modifyDate", new Date());
            }
        }
        return baseMapper.update(entity) > 0 ? entity : null;
    }

    public T getById(ID id) {

        if (id == null) {
            return null;
        }

        return baseMapper.getById(id);
    }

    public PageInfo<T> selectList(T entity, PageInfo<T> pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(baseMapper.selectList(entity));
    }

    public List<T> selectList(T entity) {
        return baseMapper.selectList(entity);
    }

    /**
     * 设置对象的属性值,若属性值为空则进行设置
     *
     * @param entity
     * @param property
     * @param value
     */
    protected void setPropertyValue(T entity, String property, Object value) {
        setPropertyValue(entity, property, value, false);
    }

    /**
     * 设置对象的值
     *
     * @param entity
     * @param property
     * @param value
     * @param isForced 是否强制设置
     */
    protected void setPropertyValue(T entity, String property, Object value, boolean isForced) {

        try {
            Assert.notNull(entity);
            Assert.hasText(property);
            Assert.notNull(value);
            Class propertyType = PropertyUtils.getPropertyType(entity, property);

            if (propertyType != value.getClass()) {
                return;
            }

            if (isForced) {
                PropertyUtils.setProperty(entity, property, value);
            } else {
                Object propValue = PropertyUtils.getProperty(entity, property);
                if (propValue == null) {
                    PropertyUtils.setProperty(entity, property, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
