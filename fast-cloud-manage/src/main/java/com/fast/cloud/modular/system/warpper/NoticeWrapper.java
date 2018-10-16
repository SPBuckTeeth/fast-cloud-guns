package com.fast.cloud.modular.system.warpper;

import com.fast.cloud.core.base.warpper.BaseControllerWarpper;
import com.fast.cloud.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String creater = (String) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
