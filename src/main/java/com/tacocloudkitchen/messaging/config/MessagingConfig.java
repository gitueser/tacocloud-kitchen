package com.tacocloudkitchen.messaging.config;

import com.tacocloudkitchen.dto.TacoOrder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {

    @Bean
    @Profile({"jms-template", "jms-listener"})
    public MappingJackson2MessageConverter jmsMessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("order", TacoOrder.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }

    @Bean
    @Profile({"rabbitmq-template", "rabbitmq-listener"})
    public Jackson2JsonMessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Profile("kafka-listener")
    public ConsumerFactory<String, TacoOrder> createTacoOrderConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "tacocloud_kitchen");

        JsonDeserializer<TacoOrder> tacoOrderJsonDeserializer = new JsonDeserializer<>(TacoOrder.class);
        tacoOrderJsonDeserializer.addTrustedPackages("com.tacocloudkitchen.dto.TacoOrder");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), tacoOrderJsonDeserializer);
    }

    @Bean("NotificationContainerFactory")
    @Profile("kafka-listener")
    public ConcurrentKafkaListenerContainerFactory<String, TacoOrder> createOrderKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TacoOrder> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createTacoOrderConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
