package com.wu.admin.pojo.quartz.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description : 定时任务执行记录
 * Time : 2017 10 30 下午4:10
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Entity
@Table(name = "auth_job_detail_call_notes")
@DynamicInsert
@DynamicUpdate
public class JobDetailCallNotesDO {

    @Id
    private Integer id;

    @Column
    private Integer jobId;

    @Column
    private String reqSequence;

    @Column
    private Date reqTime;

    @Column
    private Integer state;

    @Column
    private Integer responseCode;

    @Column
    private String responseMsg;

    @Column
    private Date createAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getReqSequence() {
        return reqSequence;
    }

    public void setReqSequence(String reqSequence) {
        this.reqSequence = reqSequence;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "JobDetailCallNotesDO{" +
                "id=" + id +
                "jobId=" + jobId +
                ", reqSequence='" + reqSequence + '\'' +
                ", reqTime=" + reqTime +
                ", state=" + state +
                ", responseCode=" + responseCode +
                ", responseMsg='" + responseMsg + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
