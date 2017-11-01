package com.wu.admin.web.ao;

import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author :  wubo
 * @version :  1.0.0
 *          create time : 2017 10 24 下午2:53
 */
public class AdminAddAO implements Serializable{

    private String userName;

    private String userEmail;

    private String password;

    private String roleId;

    private String salt;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "AdminAddAO{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", roleId='" + roleId + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
