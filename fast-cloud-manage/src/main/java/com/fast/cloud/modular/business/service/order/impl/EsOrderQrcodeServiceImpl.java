package com.fast.cloud.modular.business.service.order.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.fast.cloud.modular.business.service.order.IEsOrderQrcodeService;
import com.fast.cloud.modular.business.model.order.EsOrderQrcode;
import com.fast.cloud.modular.business.dao.order.EsOrderQrcodeMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lossdate
 * @since 2018-09-12
 */
@Service
public class EsOrderQrcodeServiceImpl extends ServiceImpl<EsOrderQrcodeMapper, EsOrderQrcode> implements IEsOrderQrcodeService {
    @Autowired
    private EsOrderQrcodeMapper esOrderQrcodeMapper;

    @Override
    public List<EsOrderQrcode> getEsOrderQrcode(Page<EsOrderQrcode> page) {
        return esOrderQrcodeMapper.getEsOrderQrcode(page);
    }

    @Override
    public List<EsOrderQrcode> getEsOrderQrcode2(Page<EsOrderQrcode> page, String beginTime, String endTime, String batch, String orderId, String status, String orderByField, boolean asc) {
        return this.baseMapper.getEsOrderQrcode2(page, beginTime, endTime, batch, orderId, status, orderByField, asc);
    }
}
