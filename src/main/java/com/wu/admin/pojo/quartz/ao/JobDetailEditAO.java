package com.wu.admin.pojo.quartz.ao;

import com.wu.admin.pojo.quartz.entity.JobDetailDO;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 30 下午5:52
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobDetailEditAO {

    /**
     * 修改之前的jobDetail
     */
    private JobDetailDO jobDetailDO;

    private String origCron;

    private Integer origState;

    private Integer editType;

    public JobDetailDO getJobDetailDO() {
        return jobDetailDO;
    }

    public void setJobDetailDO(JobDetailDO jobDetailDO) {
        this.jobDetailDO = jobDetailDO;
    }

    public String getOrigCron() {
        return origCron;
    }

    public void setOrigCron(String origCron) {
        this.origCron = origCron;
    }

    public Integer getOrigState() {
        return origState;
    }

    public void setOrigState(Integer origState) {
        this.origState = origState;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    @Override
    public String toString() {
        return "JobDetailEditAO{" +
                "jobDetailDO=" + jobDetailDO +
                ", origCron='" + origCron + '\'' +
                ", origState=" + origState +
                '}';
    }
}
