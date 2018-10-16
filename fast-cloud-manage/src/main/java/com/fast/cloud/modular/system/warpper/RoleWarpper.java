package com.fast.cloud.modular.system.warpper;

import com.fast.cloud.core.base.warpper.BaseControllerWarpper;
import com.fast.cloud.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((String) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((String) map.get("deptid")));
    }

}
