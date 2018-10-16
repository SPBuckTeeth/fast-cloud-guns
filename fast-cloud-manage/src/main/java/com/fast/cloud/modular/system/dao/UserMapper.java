package com.fast.cloud.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fast.cloud.core.datascope.DataScope;
import com.fast.cloud.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") String userId, @Param("status") int status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") String userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") String deptid);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") String userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);
}