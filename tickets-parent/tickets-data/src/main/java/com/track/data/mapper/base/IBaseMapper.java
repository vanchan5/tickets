package com.track.data.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.track.data.bo.user.permission.PermissionBo;
import com.track.data.bo.user.permission.RoleBo;
import com.track.data.domain.po.test.TbUserPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-09-01 21:41
 *
 * 自定义 MyBatis IBaseMapper
 */
public interface IBaseMapper<T> extends BaseMapper<T> {

    /**
     * 测试框架
     *
     * @param username
     * @return
     */
    Map<String, Object> findByUserName(String username);


    /**
     *判断id是否存在
     * @param value 值
     * @param tableName 表名称
     * @param fields 数据要过滤的字段,多个用逗号隔开  如 id=#{value}
     * @return
     */
    int countById(@Param("value") Object value,
                  @Param("tableName") String tableName,
                  @Param("field")String fields,
                  @Param("concatWhereSql") String concatWhereSql
    );

    TbUserPo findByUserNames(String username);

    /**
     * @Author chauncy
     * @Date 2019-10-19 18:00
     * @Description //根据用户id获取绑定的角色
    *
     * @Update chauncy
     *
     * @param  userId
     * @return java.util.List<com.track.data.bo.user.permissions.RoleBo>
     **/
    List<RoleBo> findUserRoleByUserId(Long userId);

    /**
     * @Author chauncy
     * @Date 2019-10-19 18:04
     * @Description //根据用户id获取对应的权限
     *
     * @Update chauncy
     *
     * @param  id
     * @return java.util.List<com.track.data.bo.user.permissions.PermissionBo>
     **/
    List<PermissionBo> findPermissionByUserId(Long id);
}
