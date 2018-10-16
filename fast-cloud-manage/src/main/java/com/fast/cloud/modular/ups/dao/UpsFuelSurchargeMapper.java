package com.fast.cloud.modular.ups.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.ups.model.UpsFuelSurcharge;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Ups燃油附加费表 Mapper 接口
 * </p>
 *
 * @author lossdate
 * @since 2018-10-15
 */
public interface UpsFuelSurchargeMapper extends BaseMapper<UpsFuelSurcharge> {

    /**
     * 燃油附加费管理列表
     * @param page 分页数据
     * @param effectiveDate 生效日期
     * @param surcharge 附加率(%)
     * @param orderByField 排序
     * @param asc 排序
     * @return 燃油附加费管理列表
     */
    List<UpsFuelSurcharge> getUpsFuelSurcharge(@Param("page") Page<UpsFuelSurcharge> page,
                                               @Param("effectiveDate") String effectiveDate,
                                               @Param("surcharge") String surcharge,
                                               @Param("orderByField") String orderByField,
                                               @Param("isAsc") boolean asc);
}
