package com.fast.cloud.modular.sysproduct.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.model.Product;
import com.fast.cloud.modular.sysproduct.dao.ProductMapper;
import com.fast.cloud.modular.sysproduct.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统企业表 服务实现类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductList(Page<Product> page, String beginTime, String endTime, String enterpriseName, String servicePlatform, String status, String orderByField, boolean asc) {
        return this.baseMapper.getProductList(page, beginTime, endTime, enterpriseName, servicePlatform, status, orderByField, asc);
    }

    @Override
    public long checkRepetition(Product product) {
        return productMapper.checkRepetition(product);
    }
}
