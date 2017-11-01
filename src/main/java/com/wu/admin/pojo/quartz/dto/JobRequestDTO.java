package com.wu.admin.pojo.quartz.dto;

import java.io.Serializable;

/**
 * Description : 请求dto封装
 * Time : 2017 10 30 下午2:58
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobRequestDTO implements Serializable {


    /**
     *请求编号
     */
    private String reqSequence;
    /**
     * 任务名称代码
     */
    private String jobNameCode;

    /**
     * 任务组代码
     */
    private String jobGroupCode;

    /**
     *请求附加参数
     */
    private String extraParams;

    /**
     * 请求编号+请求附加参数+预先约定的token，md5摘要。
     */
    private String sign;


    public String getReqSequence() {
        return reqSequence;
    }

    public void setReqSequence(String reqSequence) {
        this.reqSequence = reqSequence;
    }

    public String getJobNameCode() {
        return jobNameCode;
    }

    public void setJobNameCode(String jobNameCode) {
        this.jobNameCode = jobNameCode;
    }

    public String getJobGroupCode() {
        return jobGroupCode;
    }

    public void setJobGroupCode(String jobGroupCode) {
        this.jobGroupCode = jobGroupCode;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "JobRequestDTO{" +
                "reqSequence='" + reqSequence + '\'' +
                ", jobNameCode='" + jobNameCode + '\'' +
                ", jobGroupCode='" + jobGroupCode + '\'' +
                ", extraParams='" + extraParams + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
