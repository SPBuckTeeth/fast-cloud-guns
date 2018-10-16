package com.fast.cloud.modular.ups.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.ups.model.UpsFuelSurcharge;
import com.fast.cloud.modular.ups.dao.UpsFuelSurchargeMapper;
import com.fast.cloud.modular.ups.service.IUpsFuelSurchargeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Ups燃油附加费表 服务实现类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-15
 */
@Service
public class UpsFuelSurchargeServiceImpl extends ServiceImpl<UpsFuelSurchargeMapper, UpsFuelSurcharge> implements IUpsFuelSurchargeService {

    @Override
    public List<UpsFuelSurcharge> getUpsFuelSurcharge(Page<UpsFuelSurcharge> page, String effectiveDate, String surcharge, String orderByField, boolean asc) {
        return this.baseMapper.getUpsFuelSurcharge(page, effectiveDate, surcharge, orderByField, asc);
    }
}
