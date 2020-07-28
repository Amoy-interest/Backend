package com.example.amoy_interest.common;

import com.example.amoy_interest.exception.CustomException;
import com.example.amoy_interest.exception.CustomUnauthorizedException;
import com.example.amoy_interest.msgutils.Msg;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 捕捉所有Shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Msg handle401(ShiroException e) {
        return new Msg(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage(), null);
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Msg handle401(UnauthorizedException e) {
        return new Msg(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")", null);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public Msg handle401(UnauthenticatedException e) {
        return new Msg(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)", null);
    }

    /**
     * 捕捉UnauthorizedException自定义异常
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public Msg handle401(CustomUnauthorizedException e) {
        return new Msg(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage(), null);
    }

    /**
     * 捕捉校验异常(BindException)
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Msg validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return new Msg(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
    }

//    /**
//     * 捕捉校验异常(MethodArgumentNotValidException)
//     * @return
//     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Msg validException(MethodArgumentNotValidException e) {
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        Map<String, Object> result = this.getValidError(fieldErrors);
//        return new Msg(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
//    }

    /**
     * 捕捉其他所有自定义异常
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public Msg handle(CustomException e) {
        return new Msg(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

//    /**
//     * 捕捉404异常
//     * @return
//     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Msg handle(NoHandlerFoundException e) {
//        return new Msg(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
//    }

//    /**
//     * 捕捉其他所有异常
//     * @param request
//     * @param ex
//     * @return
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public Msg globalException(HttpServletRequest request, Throwable ex) {
//        return new Msg(this.getStatus(request).value(), ex.toString() + ": " + ex.getMessage(), null);
//    }
    @ExceptionHandler(Exception.class)
    public Msg handleException(Exception e) {
        log.error(e.getMessage(),e);
        return new Msg(500,"系统繁忙，请稍后再试");
    }

    /**
     * 获取状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

}
