package com.example.srpingbotproject.config;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BotConfigValidatorTest {



    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final List<String> required = Arrays.asList("botName", "botToken" +
            "", "botOwner");


    @Test
    public void testBotConfig(){
        var properties = new BotConfig();
        properties.setBotName("Bot");
        properties.setBotToken("123");
        properties.setBotOwner("4444");
        var errors = validator.validate(properties);
        Assertions.assertEquals(0, errors.size(), "Без ошибок");

        properties = new BotConfig();
        errors = validator.validate(properties);
        ConstraintViolation[] constraintViolations  = errors.toArray(x -> new ConstraintViolation[x]);
        for(ConstraintViolation violation : constraintViolations){
            Assertions.assertEquals("не должно быть пустым", violation.getMessage());   //Не понимаю почему ошибка на русском пишется
            Assertions.assertTrue(required.contains(violation.getPropertyPath().toString()));
        }

    }
}
