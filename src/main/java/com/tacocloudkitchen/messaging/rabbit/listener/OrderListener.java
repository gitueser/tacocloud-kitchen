package com.tacocloudkitchen.messaging.rabbit.listener;

import com.tacocloudkitchen.KitchenUI;
import com.tacocloudkitchen.dto.TacoOrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("rabbitmq-listener")
@Component
public class OrderListener {

    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(TacoOrderDto order) {
        ui.displayOrder(order);
    }
}
