package com.fast.cloud.multi.service.impl;

import com.fast.cloud.core.common.constant.DatasourceEnum;
import com.fast.cloud.core.mutidatasource.annotion.DataSource;
import com.fast.cloud.multi.entity.Test;
import com.fast.cloud.multi.mapper.TestMapper;
import com.fast.cloud.multi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-10
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    @Transactional
    public void testBiz() {
        Test test = new Test();
        test.setBbb("bizTest");
        testMapper.insert(test);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_GUNS)
    @Transactional
    public void testGuns() {
        Test test = new Test();
        test.setBbb("gunsTest");
        testMapper.insert(test);
    }

    @Override
    @Transactional
    public void testAll() {
        testBiz();
        testGuns();
        //int i = 1 / 0;
    }

}
