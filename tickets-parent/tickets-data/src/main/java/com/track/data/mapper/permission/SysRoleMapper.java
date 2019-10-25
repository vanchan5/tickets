package com.track.data.mapper.permission;

import com.track.data.domain.po.permission.SysRolePo;
import com.track.data.dto.manage.permission.search.SearchRoleDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.base.BaseVo;
import com.track.data.vo.permission.role.SearchRoleVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-19
 */
public interface SysRoleMapper extends IBaseMapper<SysRolePo> {

    /**
     * @Author chauncy
     * @Date 2019-10-23 14:10
     * @Description //分页查询角色信息
     *
     * @Update chauncy
     *
     * @param  searchRoleDto
     * @return java.util.List<com.track.data.vo.permission.role.SearchRoleVo>
     **/
    List<SearchRoleVo> searchRole(SearchRoleDto searchRoleDto);

    /**
     * @Author chauncy
     * @Date 2019-10-23 18:15
     * @Description //用户关联的角色
     *
     * @Update chauncy
     *
     * @param  id
     * @return java.util.List<com.track.data.vo.base.BaseVo>
     **/
    @Select("SELECT sr.id, sr.name " +
            "FROM sys_role_user sru " +
            "LEFT JOIN sys_role sr ON sru.role_id = sr.id and sr.del_flag = 0 " +
            "WHERE sru.del_flag = 0 and user_Id = #{userId}")
    List<BaseVo> findRoleByUserId(Long id);
}
