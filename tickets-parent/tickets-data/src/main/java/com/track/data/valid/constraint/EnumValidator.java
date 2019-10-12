package com.track.data.valid.constraint;

import com.track.data.valid.annotation.EnumConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author cheng
 * @create 2019-09-01 21:09
 *
 * 校验枚举的合法性
 */
@Slf4j
public class EnumValidator implements ConstraintValidator<EnumConstraint, Object> {
    /**
     * 枚举类型
     */
    Class<?> cl;

    @Override
    public void initialize(EnumConstraint constraintAnnotation) {
        cl = constraintAnnotation.target();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {


        if (cl.isEnum()) {
            //枚举类验证
            Method method ;
            try {
                method = cl.getMethod("isExist",Object.class);
            } catch (NoSuchMethodException e) {
                log.error(String.valueOf(e));
                constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                //重新添加错误提示语句
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
                return false;
                // throw new ServiceException(ResultCode.SYSTEM_ERROR,"枚举类型验证出错：枚举类缺少isExist方法");
            }
            /**
             * 枚举类没有instance方法
             */
            Object[] enumInstance = cl.getEnumConstants();

            Object isExist = null;
            try {
                //执行isExist方法
                isExist = method.invoke(enumInstance[0], value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error(String.valueOf(e));
                constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                //重新添加错误提示语句
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
                return false;
                //throw new ServiceException(ResultCode.SYSTEM_ERROR,"枚举类型验证出错：isExists方法调用出错");
            }
            return (Boolean) isExist;
        }
        else {
            constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
            //重新添加错误提示语句
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
            return false;
        }
    }
}

