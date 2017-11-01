package com.wu.admin.web.ao;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 31 下午5:28
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class ChangePasswdAO {

    private String origPassword;

    private String newPassword;

    public String getOrigPassword() {
        return origPassword;
    }

    public void setOrigPassword(String origPassword) {
        this.origPassword = origPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswdAO{" +
                "origPassword='" + origPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
