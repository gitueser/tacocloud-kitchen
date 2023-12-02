package com.tacocloudkitchen.dto;

import lombok.Data;

@Data
public class IngredientDto {

    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
