package com.wu.admin.pojo.quartz.dto;

import java.io.Serializable;

/**
 * Description : 返回dto
 * Time : 2017 10 30 下午2:58
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobResponseDTO implements Serializable {

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
     * 返回编号，0失败；1成功；2未知。
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * reqSequence+jobNameCode+jobGroupCode+code+message+预先约定的token，做md5摘要。
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "JobResponseDTO{" +
                "reqSequence='" + reqSequence + '\'' +
                ", jobNameCode='" + jobNameCode + '\'' +
                ", jobGroupCode='" + jobGroupCode + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
