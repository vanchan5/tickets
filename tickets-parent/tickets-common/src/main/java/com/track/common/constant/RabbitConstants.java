package com.track.common.constant;

/**
 * @author yeJH
 * @since 2019/11/2 13:46
 * <p>
 * 消息队列常量
 */
public interface RabbitConstants {
    /**
     * 延迟待付款队列名称
     */
    static final String ORDER_UNPAID_DELAY_QUEUE = "order.unpaid.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
    static final String ORDER_UNPAID_DELAY_EXCHANGE = "order.delay.exchange";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
    static final String DELAY_ROUTING_KEY = "order.delay.key";

    /**
     * 关闭订单队列
     */
    static final String CLOSE_ORDER_QUEUE = "close.order.queue";
    /**
     * 关闭订单交换机
     */
    static final String CLOSE_ORDER_EXCHANGE = "close.order.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String ROUTING_KEY = "all";

    /**
     * 延迟退款队列名称
     */
    static final String ORDER_REFUND_DELAY_QUEUE = "order.refund.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
    static final String ORDER_REFUND_DELAY_EXCHANGE = "order.refund.delay.exchange";
    /**
     * 操作退款
     */
    static final String REFUND_ORDER_QUEUE = "refund.order.queue";
    /**
     * 操作退款交换机
     */
    static final String REFUND_ORDER_EXCHANGE = "refund.order.exchange";


}
