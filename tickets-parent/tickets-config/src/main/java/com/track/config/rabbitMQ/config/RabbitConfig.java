package com.track.config.rabbitMQ.config;

import com.track.common.constant.RabbitConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeJH
 * @since 2019/11/2 13:58
 * <p>
 * 消息队列配置
 */
@Configuration
public class RabbitConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);
    /**
     * 延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * TODO 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * TODO 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/

    @Bean
    public Queue delayOrderQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", RabbitConstants.CLOSE_ORDER_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", RabbitConstants.ROUTING_KEY);
        return new Queue(RabbitConstants.ORDER_UNPAID_DELAY_QUEUE, true, false, false, params);
    }

    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * TODO 它不像 TopicExchange 那样可以使用通配符适配多个
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange delayOrderExchange() {
        return new DirectExchange(RabbitConstants.ORDER_UNPAID_DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayOrderQueue()).to(delayOrderExchange()).with(RabbitConstants.DELAY_ROUTING_KEY);
    }

    @Bean
    public Queue submitOrderQueue() {
        return new Queue(RabbitConstants.CLOSE_ORDER_QUEUE, true);
    }

    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
     * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
     **/
    @Bean
    public TopicExchange submitOrderTopicExchange() {
        return new TopicExchange(RabbitConstants.CLOSE_ORDER_EXCHANGE);
    }

    @Bean
    public Binding submitOrderBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(submitOrderQueue()).to(submitOrderTopicExchange()).with(RabbitConstants.ROUTING_KEY);
    }

    /**==============================退款消息开始====================================*/

    @Bean
    public Queue refundOrderDelayQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", RabbitConstants.REFUND_ORDER_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", RabbitConstants.ROUTING_KEY);
        return new Queue(RabbitConstants.ORDER_REFUND_DELAY_QUEUE, true, false, false, params);
    }

    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * TODO 它不像 TopicExchange 那样可以使用通配符适配多个
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange refundOrderDelayExchange() {
        return new DirectExchange(RabbitConstants.ORDER_REFUND_DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxRefundOrderBinding() {
        return BindingBuilder.bind(refundOrderDelayQueue()).to(refundOrderDelayExchange()).with(RabbitConstants.DELAY_ROUTING_KEY);
    }

    @Bean
    public Queue refundOrderQueue() {
        return new Queue(RabbitConstants.REFUND_ORDER_QUEUE, true);
    }

    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
     * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
     **/
    @Bean
    public TopicExchange refundOrderTopicExchange() {
        return new TopicExchange(RabbitConstants.REFUND_ORDER_EXCHANGE);
    }

    @Bean
    public Binding refundOrderBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(refundOrderQueue()).to(refundOrderTopicExchange()).with(RabbitConstants.ROUTING_KEY);
    }

    /**==============================退款消息结束====================================*/
}
