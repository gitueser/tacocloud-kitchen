package com.tacocloudkitchen;

import com.tacocloudkitchen.dto.TacoOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KitchenUI {

    public void displayOrder(TacoOrderDto order) {
        log.info("RECEIVED ORDER: " + order);
    }
}
