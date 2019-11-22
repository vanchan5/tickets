package com.track.config.rabbitMQ.handler;

import com.rabbitmq.client.Channel;
import com.track.common.constant.RabbitConstants;
import com.track.common.enums.manage.order.OrderStateEnum;
import com.track.common.utils.LoggerUtil;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.dto.manage.order.search.OrderRefundDto;
import com.track.order.service.IOmOrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/11/2 17:20
 * <p>
 * 消息队列： 订单下单之后半小时内没有支付订单取消
 */
@Component
public class RabbitOrderHandler {

    @Autowired
    private IOmOrderService orderService;

    @RabbitListener(queues = {RabbitConstants.CLOSE_ORDER_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void closeOrderQueue(Long orderId, Message message, Channel channel) {
        LoggerUtil.info(String.format("[closeOrderQueue 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), orderId));
        OmOrderPo omOrderPo = orderService.getById(orderId);
        //如果订单状态为未支付,就去取消订单
        if (null != omOrderPo && omOrderPo.getState().equals(OrderStateEnum.WAIT_PAY.getId())) {
            orderService.closeOrder(orderId);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }
    @RabbitListener(queues = {RabbitConstants.REFUND_ORDER_QUEUE})
    @Transactional(rollbackFor = Exception.class)
    public void refundOrderQueue(OrderRefundDto orderRefundDto, Message message, Channel channel) {
        LoggerUtil.info(String.format("[refundOrderQueue 监听的消息] - [消费时间] - [%s] - [场次id] - [%s] - [操作时间]" +
                        " - [%s]", LocalDateTime.now(), orderRefundDto.getSceneId(), orderRefundDto.getOperationTime()));
        orderService.achieveRefund(orderRefundDto);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }
}
