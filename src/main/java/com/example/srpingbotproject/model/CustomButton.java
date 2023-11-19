package com.example.srpingbotproject.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomButton {
    String text;
    String callbackData;
}
