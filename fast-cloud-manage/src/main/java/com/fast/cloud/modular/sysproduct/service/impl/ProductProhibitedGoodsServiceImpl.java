package com.fast.cloud.modular.sysproduct.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.sysproduct.dao.ProductProhibitedGoodsMapper;
import com.fast.cloud.modular.sysproduct.vo.ProductProhibitedGoodsVO;
import com.fast.cloud.modular.sysproduct.model.ProductProhibitedGoods;
import com.fast.cloud.modular.sysproduct.service.IProductProhibitedGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lossdate
 * @since 2018-10-11
 */
@Service
public class ProductProhibitedGoodsServiceImpl extends ServiceImpl<ProductProhibitedGoodsMapper, ProductProhibitedGoods> implements IProductProhibitedGoodsService {

    @Override
    public List<ProductProhibitedGoodsVO> getProductProhibitedGoods(Page<ProductProhibitedGoodsVO> page, String sysProductId, String name, String orderByField, boolean asc) {
        return this.baseMapper.getProductProhibitedGoods(page, sysProductId, name, orderByField, asc);
    }
}
