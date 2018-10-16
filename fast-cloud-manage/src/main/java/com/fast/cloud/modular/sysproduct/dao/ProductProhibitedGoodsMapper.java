package com.fast.cloud.modular.sysproduct.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.vo.ProductProhibitedGoodsVO;
import com.fast.cloud.modular.sysproduct.model.ProductProhibitedGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lossdate
 * @since 2018-10-11
 */
public interface ProductProhibitedGoodsMapper extends BaseMapper<ProductProhibitedGoods> {

    /**
     * 获取违禁品列表
     * @param page 分页数据
     * @param sysProductId 产品id
     * @param name 违禁品名
     * @param orderByField 排序
     * @param asc 排序
     * @return 违禁品列表
     */
    List<ProductProhibitedGoodsVO> getProductProhibitedGoods(@Param("page") Page<ProductProhibitedGoodsVO> page,
                                                             @Param("sysProductId") String sysProductId,
                                                             @Param("name") String name,
                                                             @Param("orderByField") String orderByField,
                                                             @Param("isAsc") boolean asc);
}
