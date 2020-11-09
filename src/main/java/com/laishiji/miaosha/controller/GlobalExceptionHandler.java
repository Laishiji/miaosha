package com.laishiji.miaosha.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.response.CommonReturnType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 解决CommonController无法解决404， 405异常的问题
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType doError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception ex) {
        ex.printStackTrace();

        Map<String,Object> responseData = new HashMap<>();

        if( ex instanceof BusinessException){

            BusinessException businessException = (BusinessException)ex;
            responseData.put("errorCode",businessException.getErrorCode());
            responseData.put("errMessage",businessException.getErrorMessage());

        }else if(ex instanceof ServletRequestBindingException){

            responseData.put("errorCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMessage","url绑定路由问题");//405

        }else if(ex instanceof NoHandlerFoundException){

            responseData.put("errorCode",EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMessage","没有找到对应的访问路径");//404

        }else{

            responseData.put("errorCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMessage",EnumBusinessError.UNKNOWN_ERROR.getErrorMessage());

        }
        return CommonReturnType.create(responseData,"fail");
    }
}