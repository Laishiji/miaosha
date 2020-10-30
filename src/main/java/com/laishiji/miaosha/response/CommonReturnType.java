package com.laishiji.miaosha.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 该类用于归一化ResponseBody的返回值，
 * 而摒弃了发生异常时，返回http状态码和内嵌tomcat的错误页面
 */
@Setter
@Getter
public class CommonReturnType {

    //表明对应请求的返回处理结果"success"或"fail"
    private String status;

    //若status==success,则data返回前端需要的json
    //若status==fail,则data则返回我们定义的状态码
    private Object data;

    /**
     * 使用方法重载的方式，默认返回正确信息，只在异常处理时调用另一个重载方法
     * @param result
     * @return
     */
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    /**
     * 返回错误信息
     * @param result
     * @param status
     * @return
     */
    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
}
