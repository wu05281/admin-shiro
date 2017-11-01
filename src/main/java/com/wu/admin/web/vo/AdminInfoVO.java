package com.wu.admin.web.vo;

import java.util.Date;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/12
 * Time : 下午4:31
 */
public class AdminInfoVO {
    private String uid;

    private String roleId;

    private String userName;

    private String userEmail;

    private Date updatedAt;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "AdminInfoVO{" +
                "uid='" + uid + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
