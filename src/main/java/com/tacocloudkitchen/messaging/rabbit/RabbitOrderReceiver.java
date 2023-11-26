package com.tacocloudkitchen.messaging.rabbit;

import com.tacocloudkitchen.dto.TacoOrder;
import com.tacocloudkitchen.messaging.OrderReceiver;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("rabbitmq-listener")
public class RabbitOrderReceiver implements OrderReceiver {

    private final RabbitTemplate rabbit;

    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public TacoOrder receiveOrder() throws JMSException {
        TacoOrder tacoOrder = rabbit.receiveAndConvert("tacocloud.order.queue",
                new ParameterizedTypeReference<TacoOrder>() {
                }
        );
        log.info("Getting order from RabbitMQ: {}", tacoOrder);
        return tacoOrder;
    }
}
