package com.wu.admin.pojo.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:41
 */
@Entity
@Table(name = "auth_role")
public class RoleDO {

    @Id
    @Column(name = "role_id")
    @GenericGenerator(name = "roleId",strategy = "assigned")
    @GeneratedValue(generator = "roleId")
    private String roleId;

    @NotEmpty(message="角色名称不能为空")
    private String roleName;

    @Column
    private String roleDesc;

    @Column
    private Boolean enable;

    private Boolean editEnable;

    @Column
    @org.hibernate.annotations.CreationTimestamp
    private Date createdAt;

    @Column
    @org.hibernate.annotations.UpdateTimestamp
    private Date updatedAt;

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    //角色 -- 权限关系：多对多关系;
    @Transient
    @OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private List<RoleMenuDO> roleMenuList;

    // 用户 - 角色关系定义;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEditEnable() {
        return editEnable;
    }

    public void setEditEnable(Boolean editEnable) {
        this.editEnable = editEnable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSort() {
        if(StringUtils.isEmpty(sort)){
            return "createdAt";
        }else{
            return sort;
        }
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        if(StringUtils.isEmpty(sort)){
            return "desc";
        }else{
            return order;
        }
    }

    public void setOrder(String order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", enable=" + enable +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
