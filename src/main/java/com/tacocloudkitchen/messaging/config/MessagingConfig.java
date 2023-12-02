package com.tacocloudkitchen.messaging.config;

import com.tacocloudkitchen.dto.TacoOrderDto;
import com.tacocloudkitchen.messaging.kafka.listener.TacoOrderMessageDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "tacocloud_kitchen";
    private static final String CONSUMER_APP_ID = "consumer_id";
    private static final String EARLIEST = "earliest";

    @Bean
    @Profile({"jms-template", "jms-listener"})
    public MappingJackson2MessageConverter jmsMessageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("order", TacoOrderDto.class);
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
    public ConsumerFactory<String, TacoOrderDto> createTacoOrderConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TacoOrderMessageDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("NotificationContainerFactory")
    @Profile("kafka-listener")
    public ConcurrentKafkaListenerContainerFactory<String, TacoOrderDto> createOrderKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TacoOrderDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createTacoOrderConsumerFactory());
        return factory;
    }
}
