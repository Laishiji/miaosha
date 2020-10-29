package com.laishiji.miaosha.error;

/**
 * 装饰器业务异常类实现，使用了装饰器模式
 */
public class BusinessException extends Exception implements CommonError{

    //使用枚举错误
    private CommonError commonError;

    //使用已经提供的枚举错误
    public BusinessException(CommonError commonError){
        super();//Throwable初始化
        this.commonError = commonError;
    }

    //自定义errorMessage，改写枚举错误中的errorMessage
    public BusinessException(CommonError commonError,String errorMessage){
        super();
        this.commonError = commonError;
        this.commonError.setErrorMessage(errorMessage);
    }

    @Override
    public int getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return commonError.getErrorMessage();
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        commonError.setErrorMessage(errorMessage);
        return this;
    }
}
