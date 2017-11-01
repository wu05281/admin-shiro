package com.wu.admin.web.ao;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 26 上午9:15
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobAddAO {

    private String nameCode;

    private String groupCode;

    private String description;

    private String callUrl;

    private String cron;

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "JobAddAO{" +
                "nameCode='" + nameCode + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", description='" + description + '\'' +
                ", callUrl='" + callUrl + '\'' +
                ", cron='" + cron + '\'' +
                '}';
    }
}
