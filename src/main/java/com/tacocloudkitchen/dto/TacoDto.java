package com.tacocloudkitchen.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoDto {
    private String name;
    private Date createdAt;
    private List<String> ingredients = new ArrayList<>();
}
