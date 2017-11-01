package com.wu.admin.pojo.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:45
 */
@Entity
@Table(name = "auth_admin_role")
public class AdminRoleDO implements Serializable {
    @Id
    @Column(name = "admin_id")
    private String adminId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    /**
     * @return admin_id
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * @param adminId
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * @return role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
