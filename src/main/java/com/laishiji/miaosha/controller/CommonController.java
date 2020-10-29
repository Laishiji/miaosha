package com.laishiji.miaosha.controller;

import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 该Controller定义所有Controller通用的方法，其他Controller需要继承该类
 */
public class CommonController {
    /**
     * 定义异常处理器捕获处理Controller向上抛出的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e){
        //直接返回e或者businessException的话, json中会包含很多虚拟机栈跟踪信息,
        //因此使用map取出错误状态码和错误信息即可
        Map<String, Object> responseData = new HashMap<>();
        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException)e;

            responseData.put("errorCode", businessException.getErrorCode());
            responseData.put("errorMessage", businessException.getErrorMessage());
        }else {
            responseData.put("errorCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMessage", EnumBusinessError.UNKNOWN_ERROR.getErrorMessage());
        }

        //归一化处理，返回通用对象
        return CommonReturnType.create(responseData, "fail");
    }
}
