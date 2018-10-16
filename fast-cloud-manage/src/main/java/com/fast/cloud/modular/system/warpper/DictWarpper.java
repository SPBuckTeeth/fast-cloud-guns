package com.fast.cloud.modular.system.warpper;

import com.fast.cloud.core.base.warpper.BaseControllerWarpper;
import com.fast.cloud.core.util.ToolUtil;
import com.fast.cloud.modular.system.model.Dict;
import com.fast.cloud.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 字典列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        String id = map.get("id").toString();
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if(dicts != null){
            for (Dict dict : dicts) {
                detail.append(dict.getCode() + ":" +dict.getName() + ",");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(),","));
        }
    }

}
