package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmSceneRelGradePo;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.applet.ticket.CommodityAttrVo;
import com.track.data.vo.applet.ticket.SceneRelGradeInfoVo;

import java.util.List;

/**
 * <p>
 * 档次跟场次关联表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmSceneRelGradeMapper extends IBaseMapper<OmSceneRelGradePo> {

    /**
     * @Author yeJH
     * @Date 2019/10/30 14:22
     * @Description 获取场次跟档次关联的信息  座位号
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.vo.applet.ticket.SceneRelGradeInfoVo>
     **/
    List<SceneRelGradeInfoVo> getSceneRelGradeInfoList(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/11/1 18:22
     * @Description 根据场次档次获取门票所剩余的座位数
     *
     * @Update yeJH
     *
     * @param  relId 场次档次关联的id
     * @return com.track.data.domain.po.ticket.OmSceneRelGradePo
     **/
    OmSceneRelGradePo getRemainingSum(Long relId);

    /**
     * @Author yeJH
     * @Date 2019/11/1 12:50
     * @Description 新增/编辑门票信息时 根据已插入的场次，档次获取要插入的关联座位信息
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.domain.po.ticket.OmSceneRelGradePo>
     **/
    List<OmSceneRelGradePo> getInsertRelInfo(Long ticketId);

    /**
     * @Author yeJH
     * @Date 2019/11/15 22:39
     * @Description 获取场次跟档次关联的信息（规格）
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return java.util.List<com.track.data.vo.applet.ticket.CommodityAttrVo>
     **/
    List<CommodityAttrVo> getCommodityAttr(Long ticketId);
}
