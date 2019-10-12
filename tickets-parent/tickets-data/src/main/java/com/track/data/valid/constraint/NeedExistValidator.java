package com.track.data.valid.constraint;

import com.alibaba.fastjson.JSON;
import com.track.data.valid.annotation.NeedExistConstraint;
import com.track.data.domain.po.test.TbUserPo;
import com.track.data.mapper.base.IBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**检查数据库中是否存在该数据
 * isNeedExists为true时，当数据库存在该数据时验证通过（用于验证外键是否存在）
 * isNeedExists为false时，当数据库存在该数据时验证不通过（用于删除时验证数据是否被引用）
 * @Author cheng
 * @create 2019-09-01 21:12
 *
 */
public class NeedExistValidator implements ConstraintValidator<NeedExistConstraint, Object> {

    @Autowired
    private IBaseMapper<TbUserPo> baseMapper;  //此处需要指定具体类型IBaseMapper<***Po>

    private String tableName="";

    private boolean isNeedExists;

    private String field;

    private String concatWhereSql;


    @Override
    public void initialize(NeedExistConstraint constraintAnnotation) {
        tableName = constraintAnnotation.tableName();
        isNeedExists=constraintAnnotation.isNeedExists();
        field=constraintAnnotation.field();
        concatWhereSql=constraintAnnotation.concatWhereSql();
    }




    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //如果是parentid可能是空，要判断非空在外面加注解@Notnull
        if (value==null|| StringUtils.isBlank(value.toString())){
            return true;
        }
        //long类型特殊处理，前端有时候空的会传0
        if (value instanceof Long){
            if (Long.parseLong(value.toString())==0){
                return true;
            }
        }
        //如果传的是集合就要一个个验证
        if (value instanceof List ||value.getClass().isArray()){
            //object转list
            List<String> ids= JSON.parseArray(JSON.toJSONString(value),String.class);
            for (String id:ids){
                int count = baseMapper.countById(id, tableName,field,concatWhereSql);
                if (count==0){
                    if (isNeedExists) {
                        constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                        //重新添加错误提示语句
                        constraintValidatorContext
                                .buildConstraintViolationWithTemplate(String.format("%s为【%s】在数据库中不存在！", field, id)).addConstraintViolation();
                        return false;
                    }
                }
                else {
                    if (!isNeedExists){
                        constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                        //重新添加错误提示语句
                        constraintValidatorContext
                                .buildConstraintViolationWithTemplate(String.format("%s为【%s】在数据库已存在！不允许删除！",field,id)).addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;

        }
        else {
            int count = baseMapper.countById(value, tableName,field,concatWhereSql);
            return count>0==isNeedExists;
        }
    }
}

