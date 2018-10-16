package com.fast.cloud.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fast.cloud.core.node.MenuNode;
import com.fast.cloud.modular.system.model.Menu;
import com.fast.cloud.core.node.ZTreeNode;
import com.fast.cloud.modular.system.dao.MenuMapper;
import com.fast.cloud.modular.system.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public void delMenu(String menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(String menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }

    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level) {
        return this.baseMapper.selectMenus(condition, level);
    }

    @Override
    public List<String> getMenuIdsByRoleId(String roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<String> menuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    @Override
    public int deleteRelationByMenu(String menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public List<String> getResUrlsByRoleId(String roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<String> roleIds) {
        return this.baseMapper.getMenusByRoleIds(roleIds);
    }
}