package com.wu.admin.enums;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 31 下午2:26
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public enum JobDetailEditTypeEnum {

    EDIT_CRON(1, "修改cron"), DELETE(2, "删除"), RESUME(3, "恢复");

    private Integer code;

    private String message;

    JobDetailEditTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
