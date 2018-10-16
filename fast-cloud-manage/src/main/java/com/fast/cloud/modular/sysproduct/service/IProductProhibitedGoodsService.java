package com.fast.cloud.modular.sysproduct.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.vo.ProductProhibitedGoodsVO;
import com.fast.cloud.modular.sysproduct.model.ProductProhibitedGoods;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-11
 */
public interface IProductProhibitedGoodsService extends IService<ProductProhibitedGoods> {

    /**
     * 获取违禁品列表
     * @param page 分页数据
     * @param sysProductId 产品id
     * @param name 违禁品名
     * @param orderByField 排序
     * @param asc 排序
     * @return 违禁品列表
     */
    List<ProductProhibitedGoodsVO> getProductProhibitedGoods(Page<ProductProhibitedGoodsVO> page, String sysProductId, String name, String orderByField, boolean asc);
}
