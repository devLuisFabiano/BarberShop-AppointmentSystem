package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {
    private String name;
    private String email;
}
