package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmSceneGradeRelSeatPo;
import com.track.data.mapper.base.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 档次跟场次关联表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-01
 */
public interface OmSceneGradeRelSeatMapper extends IBaseMapper<OmSceneGradeRelSeatPo> {

    /**
     * @Author yeJH
     * @Date 2019/11/1 13:42
     * @Description 新增/编辑门票信息时 保存场次，档次跟座位具体某一排的关联
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.domain.po.ticket.OmSceneGradeRelSeatPo>
     **/
    List<OmSceneGradeRelSeatPo> getInsertRelSeatInfo(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/11/2 22:49
     * @Description select for update  根据id查询场次档次关联的座位
     *
     * @Update yeJH
     *
     * @param  id
     * @return com.track.data.domain.po.ticket.OmSceneGradeRelSeatPo
     **/
    OmSceneGradeRelSeatPo selectByIdForUpdate(Long id);

    List<OmSceneGradeRelSeatPo> selectListForUpdate(Long relId);
}
