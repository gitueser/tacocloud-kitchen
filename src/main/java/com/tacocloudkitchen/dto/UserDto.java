package com.tacocloudkitchen.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
}
