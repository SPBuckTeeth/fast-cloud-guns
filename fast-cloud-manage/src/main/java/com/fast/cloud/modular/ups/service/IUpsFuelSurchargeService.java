package com.fast.cloud.modular.ups.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.ups.model.UpsFuelSurcharge;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * Ups燃油附加费表 服务类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-15
 */
public interface IUpsFuelSurchargeService extends IService<UpsFuelSurcharge> {

    /**
     * 燃油附加费管理列表
     * @param page 分页数据
     * @param effectiveDate 生效日期
     * @param surcharge 附加率(%)
     * @param orderByField 排序
     * @param asc 排序
     * @return 燃油附加费管理列表
     */
    List<UpsFuelSurcharge> getUpsFuelSurcharge(Page<UpsFuelSurcharge> page, String effectiveDate, String surcharge, String orderByField, boolean asc);
}
