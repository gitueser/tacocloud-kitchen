package com.tacocloudkitchen.messaging.jms.artemis;

import com.tacocloudkitchen.dto.TacoOrderDto;
import com.tacocloudkitchen.messaging.OrderReceiver;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("jms-listener")
public class JmsOrderReceiver implements OrderReceiver {

    private final JmsTemplate jms;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
    }

    @Override
    public TacoOrderDto receiveOrder() throws JMSException {
        TacoOrderDto tacoOrderDto = (TacoOrderDto) jms.receiveAndConvert("tacocloud.order.queue");
        log.info("Getting order from JMS: {}", tacoOrderDto);
        return tacoOrderDto;
    }
}
