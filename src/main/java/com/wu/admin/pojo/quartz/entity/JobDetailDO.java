package com.wu.admin.pojo.quartz.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Description : Created by 任务详情
 * Time : 2017 10 30 下午3:21
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Entity
@Table(name = "quartz_job_detail")
@DynamicInsert
@DynamicUpdate
public class JobDetailDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nameCode;

    @Column
    private String groupCode;

    @Column
    private String description;

    @Column
    private String callUrl;

    @Column
    private String cron;

    @Column
    private Integer state;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "JobDetailDO{" +
                "id=" + id +
                ", nameCode='" + nameCode + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", description='" + description + '\'' +
                ", callUrl='" + callUrl + '\'' +
                ", cron='" + cron + '\'' +
                ", state=" + state +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
