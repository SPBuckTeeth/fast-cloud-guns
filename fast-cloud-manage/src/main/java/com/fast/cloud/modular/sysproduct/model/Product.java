package com.fast.cloud.modular.sysproduct.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统企业表
 * </p>
 *
 * @author lossdate
 * @since 2018-10-08
 */
@TableName("sys_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 产品名
     */
    @TableField("enterprise_name")
    private String enterpriseName;
    /**
     * 服务平台
     */
    @TableField("service_platform")
    private String servicePlatform;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 0：有效，1：无效
     */
    private Long status;
    /**
     * 产品logo
     */
    private String logo;
    /**
     * 产品简介
     */
    private String intro;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getServicePlatform() {
        return servicePlatform;
    }

    public void setServicePlatform(String servicePlatform) {
        this.servicePlatform = servicePlatform;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
        "id=" + id +
        ", enterpriseName=" + enterpriseName +
        ", servicePlatform=" + servicePlatform +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", logo=" + logo +
        ", intro=" + intro +
        "}";
    }
}
