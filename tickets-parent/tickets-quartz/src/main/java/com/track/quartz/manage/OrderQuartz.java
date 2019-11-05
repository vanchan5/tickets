package com.track.quartz.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.track.common.constant.QuartzConstants;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.domain.po.quartz.QuartzJobPo;
import com.track.data.domain.po.ticket.OmTicketScenePo;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.mapper.quartz.QuartzJobMapper;
import com.track.data.mapper.ticket.OmTicketSceneMapper;
import com.track.quartz.proccessor.ScheduleUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @Description 关于订单的定时任务
 * @since 2019/11/3 17:23
 */
@Slf4j
@Component
@Data
public class OrderQuartz {

    @Autowired
    private OmTicketSceneMapper omTicketSceneMapper;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private Scheduler scheduler;

    public void orderFinish(String omTicketSceneId){

        OmTicketScenePo omTicketScenePo = omTicketSceneMapper.selectById(omTicketSceneId);
        if(null != omTicketScenePo && omTicketScenePo.getStartTime().compareTo(LocalDateTime.now()) <= 0) {
            //根据场次id获取 用户下单该场次的订单并且状态全部置为已消费
            omOrderMapper.updateOrderByScene(Long.valueOf(omTicketSceneId));
        }
        quartzJobMapper.deleteById(omTicketSceneId);
        ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(omTicketSceneId));

    }

}
