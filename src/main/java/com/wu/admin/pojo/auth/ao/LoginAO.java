package com.wu.admin.pojo.auth.ao;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * @author  : wubo
 * @version  : 2017/10/12上午10:14
 */
public class LoginAO implements Serializable{

    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;

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
}
