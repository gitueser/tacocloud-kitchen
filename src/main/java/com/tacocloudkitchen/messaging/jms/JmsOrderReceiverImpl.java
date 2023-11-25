package com.tacocloudkitchen.messaging.jms;

import com.tacocloudkitchen.OrderReceiver;
import com.tacocloudkitchen.dto.TacoOrder;
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
public class JmsOrderReceiverImpl implements OrderReceiver {

    private final JmsTemplate jms;

    @Autowired
    public JmsOrderReceiverImpl(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
    }

    @Override
    public TacoOrder receiveOrder() throws JMSException {
        TacoOrder tacoOrder = (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
        log.info("Getting order: {}", tacoOrder);
        return  tacoOrder;
    }
}
