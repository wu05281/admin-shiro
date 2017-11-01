package com.wu.admin.pojo.quartz.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 30 下午3:21
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Entity
@Table(name = "quartz_job_detail_edit_notes")
@DynamicInsert
@DynamicUpdate
public class JobDetailEditNotesDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobDetailDO jobDetailDO;

    @Column
    private Integer editType;

    @Column
    private String cron;

    @Column
    private String origCron;

    @Column
    private Integer state;

    @Column
    private Integer origState;

    @Column
    private String operatorId;

    @Column
    private String operatorName;

    @Column
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobDetailDO getJobDetailDO() {
        return jobDetailDO;
    }

    public void setJobDetailDO(JobDetailDO jobDetailDO) {
        this.jobDetailDO = jobDetailDO;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getOrigCron() {
        return origCron;
    }

    public void setOrigCron(String origCron) {
        this.origCron = origCron;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOrigState() {
        return origState;
    }

    public void setOrigState(Integer origState) {
        this.origState = origState;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "JobDetailHistoryDO{" +
                "id=" + id +
                ", jobDetailDO=" + jobDetailDO +
                ", editType=" + editType +
                ", cron='" + cron + '\'' +
                ", origCron='" + origCron + '\'' +
                ", state=" + state +
                ", origState=" + origState +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
