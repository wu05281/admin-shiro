package com.wu.admin.exception;

import com.wu.admin.pojo.auth.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author :  wubo
 * @version :  1.0.0
 *          create time : 2017 10 23 下午4:00
 */
@ControllerAdvice
@ResponseBody
public class BusinessExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BusinessException.class)
    public ResponseDTO<?> acctExceptionHandle(BusinessException ex) {
        ResponseDTO ard = new ResponseDTO(ex.getExceptionEnum().getCode(), ex.getExceptionEnum().getMessage(), null);
        logger.error("发生异常：异常代码：{},异常massage：{}", ex.getExceptionEnum().getCode(), ex.getExceptionEnum().getMessage());
        return ard;
    }
}
