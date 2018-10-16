package com.fast.cloud.modular.sysproduct.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.model.Product;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统企业表 Mapper 接口
 * </p>
 *
 * @author lossdate
 * @since 2018-10-08
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 获取产品列表
     * @param page 分页
     * @param beginTime 时间
     * @param endTime 时间
     * @param enterpriseName 产品名称
     * @param servicePlatform 产品代码
     * @param status 状态
     * @param orderByField 排序
     * @param asc 排序
     * @return 产品列表
     */
    List<Product> getProductList(@Param("page") Page<Product> page,
                                 @Param("beginTime") String beginTime,
                                 @Param("endTime") String endTime,
                                 @Param("enterpriseName") String enterpriseName,
                                 @Param("servicePlatform") String servicePlatform,
                                 @Param("status") String status,
                                 @Param("orderByField") String orderByField,
                                 @Param("isAsc") boolean asc);

    /**
     * 检查重复
     * @param product 实体
     * @return 数量
     */
    long checkRepetition(Product product);
}
