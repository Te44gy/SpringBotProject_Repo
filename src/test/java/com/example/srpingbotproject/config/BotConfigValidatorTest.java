package com.example.srpingbotproject.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

public class BotConfigValidatorTest {



    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testBotConfig(){
        var properties = new BotConfig();
        properties.setName("Bot");
        properties.setToken("123");
        properties.setOwner("4444");
        var errors = validator.validate(properties);
        Assertions.assertEquals(0, errors.size(), "Без ошибок");


    }
}
