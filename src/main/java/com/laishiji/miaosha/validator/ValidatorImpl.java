package com.laishiji.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * JavaBean Validator,使用hibernate validator的实现
 */
@Component("validator")
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * ValidatorImpl Bean初始化完成之后调用该方法，获取validator对象
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){

        final ValidationResult result = new ValidationResult();

        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);

        if(constraintViolationSet.size() > 0){//constraintViolationSet有值，说明有错误

            result.setHasErrors(true);

            //遍历错误集
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();//获取异常错误msg
                String propertyName = constraintViolation.getPropertyPath().toString();//哪个字段发生错误

                result.getErrorMsgMap().put(propertyName,errMsg);
            });

        }
        return result;
    }
}
