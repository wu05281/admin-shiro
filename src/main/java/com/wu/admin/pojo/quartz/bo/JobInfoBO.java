package com.wu.admin.pojo.quartz.bo;

import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 25 下午1:43
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobInfoBO implements Serializable {


    private static final long serialVersionUID = 6337736699431382454L;

    public JobInfoBO() {
    }

    private String nameCode;

    private String groupCode;

    private String description;

    private String callUrl;

    private String cron;

    private Integer state;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "JobInfoBO{" +
                "nameCode='" + nameCode + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", description='" + description + '\'' +
                ", callUrl='" + callUrl + '\'' +
                ", cron='" + cron + '\'' +
                ", state=" + state +
                '}';
    }
}
