package com.wu.admin.exception;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author :  wubo
 * @version :  1.0.0
 *          create time : 2017 10 23 下午3:55
 */
public enum ExceptionEnum {

    FAIL(0, "💔！操作失败，请检查输入！"),
    SUCCESS(1, "恭喜！操作成功"),

    DUPLICAT(9999,"存在重复的记录，请重新输入！"),
    NOT_EXSIT(9990,"角色不存在！"),
    ERROR_UNKNOW(-1, "发生未知异常，请联系管理员！"),


    PASSWORD_ORIGIN_FAILED(2001, "原密码输入错误，请核对之后重新输入！")
    ;

    private int code;

    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
