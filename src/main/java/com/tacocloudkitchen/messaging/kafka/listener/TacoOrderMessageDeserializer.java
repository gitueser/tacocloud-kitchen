package com.tacocloudkitchen.messaging.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacocloudkitchen.dto.TacoOrderDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TacoOrderMessageDeserializer implements Deserializer<TacoOrderDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public TacoOrderDto deserialize(String s, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), TacoOrderDto.class);
        } catch (IOException ex) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
