package com.example.amoy_interest.common;

import com.example.amoy_interest.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static int DUPLICATE_KEY_CODE = 1001;
    private static int PARAM_FAIL_CODE = 1002;
    private static int VALIDATION_CODE = 1003;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Msg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(),e);
        return new Msg(PARAM_FAIL_CODE,e.getBindingResult().getFieldError().getDefaultMessage());
    }
    @ExceptionHandler(ValidationException.class)
    public Msg handleValidationException(ValidationException e) {
        log.error(e.getMessage(),e);
        return new Msg(VALIDATION_CODE,e.getCause().getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Msg handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(),e);
        return new Msg(PARAM_FAIL_CODE,e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Msg handleNoFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(),e);
        return new Msg(404,"路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Msg handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(),e);
        return new Msg(DUPLICATE_KEY_CODE,"数据重复，请检查后提交");
    }

    @ExceptionHandler(RuntimeException.class)
    public Msg handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(),e);
        return new Msg(404,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Msg handleException(Exception e) {
        log.error(e.getMessage(),e);
        return new Msg(500,"系统繁忙，请稍后再试");
    }

}
