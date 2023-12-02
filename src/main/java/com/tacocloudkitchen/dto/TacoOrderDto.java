package com.tacocloudkitchen.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDto {
    private Date placedAt;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private UserDto user;
    private List<TacoDto> tacos = new ArrayList<>();
}
