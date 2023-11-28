package com.tacocloudkitchen.messaging.kafka.listener;

import com.tacocloudkitchen.KitchenUI;
import com.tacocloudkitchen.dto.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Profile("kafka-listener")
@Component
@Slf4j
public class OrderListener {
    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @KafkaListener(topics="tacocloud.orders.topic", containerFactory="NotificationContainerFactory")
    public void handle(TacoOrder order) {
        ui.displayOrder(order);
    }



//    @KafkaListener(topics="tacocloud.orders.topic")
//    public void handle(TacoOrder order) {
//        ui.displayOrder(order);
//    }

//    @KafkaListener(topics = "tacocloud.orders.topic")
//    public void handle(TacoOrder order, ConsumerRecord<String, TacoOrder> record) {
//        log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
//        ui.displayOrder(order);
//    }

//    @KafkaListener(topics="tacocloud.orders.topic")
//    public void handle(TacoOrder order, Message<TacoOrder> message) {
//        MessageHeaders headers = message.getHeaders();
//        log.info("Received from partition {} with timestamp {}",
//                headers.get(KafkaHeaders.RECEIVED_PARTITION),
//                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
//        ui.displayOrder(order);
//    }
}
