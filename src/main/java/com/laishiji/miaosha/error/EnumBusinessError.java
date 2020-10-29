package com.laishiji.miaosha.error;

public enum EnumBusinessError implements CommonError{
    //实际分布式开发中，不同的开发人员需要全局状态码来协调信息互通，在一个通用的文件中进行管理
    //以下方式模拟以上的做法
    //参数校验通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    UNKNOWN_ERROR(10002,"未知错误"),
    //20000开头表示用户信息相关错误定义
    USER_NOT_EXIST_ERROR(20001,"用户不存在"),
    ;

    private int errorCode;
    private String errorMessage;

    EnumBusinessError(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 自定义改变错误提示信息
     * @param errorMessage
     * @return
     */
    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
