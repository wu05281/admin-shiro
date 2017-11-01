package com.wu.admin.pojo.auth.dto;

import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/9
 * Time : 上午10:47
 */
public class ResponseDTO<T> implements Serializable {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据对象,必须命名为aaData以便jquery的dataTables调用，否则报错
     */
    private T t;

    public ResponseDTO(int code, String message, T t) {
        this.code = code;
        this.message = message;
        this.t = t;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", t=" + t +
                '}';
    }
}
