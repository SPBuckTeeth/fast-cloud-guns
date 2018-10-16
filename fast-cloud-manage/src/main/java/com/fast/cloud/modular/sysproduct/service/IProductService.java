package com.fast.cloud.modular.sysproduct.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.model.Product;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 系统企业表 服务类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-08
 */
public interface IProductService extends IService<Product> {

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
    List<Product> getProductList(Page<Product> page, String beginTime, String endTime, String enterpriseName, String servicePlatform, String status, String orderByField, boolean asc);

    /**
     * 检查重复
     * @param product 实体
     * @return 数量
     */
    long checkRepetition(Product product);
}
