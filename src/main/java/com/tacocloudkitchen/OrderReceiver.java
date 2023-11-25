package com.tacocloudkitchen;

import com.tacocloudkitchen.dto.TacoOrder;
import jakarta.jms.JMSException;

public interface OrderReceiver {

    TacoOrder receiveOrder() throws JMSException;
}
