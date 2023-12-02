package com.tacocloudkitchen.messaging;

import com.tacocloudkitchen.dto.TacoOrderDto;
import jakarta.jms.JMSException;

public interface OrderReceiver {

    TacoOrderDto receiveOrder() throws JMSException;
}
