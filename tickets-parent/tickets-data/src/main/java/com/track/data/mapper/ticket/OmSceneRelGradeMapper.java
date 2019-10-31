package com.track.data.mapper.ticket;

import com.track.data.domain.po.ticket.OmSceneRelGradePo;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.mapper.base.IBaseMapper;
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
     * @Date 2019/10/31 12:26
     * @Description 根据场次档次获取门票所剩余的座位数
     *
     * @Update yeJH
     *
     * @param  orderSubmitDto 提交订单参数
     * @return java.lang.Integer
     **/
    Integer getRemainingSum(OrderSubmitDto orderSubmitDto);
}
