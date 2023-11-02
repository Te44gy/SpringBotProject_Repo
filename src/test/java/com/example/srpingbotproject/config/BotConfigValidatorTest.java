package com.example.srpingbotproject.config;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BotConfigValidatorTest {

    private static Validator validator;
    private final List<String> required = Arrays.asList("name", "token", "owner");

    @BeforeAll
    static void init(){
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testBotConfig(){
        var properties = new BotConfig();
        properties.setName("Bot");
        properties.setToken("123");
        properties.setOwner("4444");
        var errors = validator.validate(properties);
        Assertions.assertEquals(0, errors.size(), "Без ошибок");

        properties = new BotConfig();
        errors = validator.validate(properties);
        ConstraintViolation[] constraintViolations  = errors.toArray(x -> new ConstraintViolation[x]);
        for(ConstraintViolation violation : constraintViolations){
            Assertions.assertEquals("не должно быть пустым", violation.getMessage());
            Assertions.assertTrue(required.contains(violation.getPropertyPath().toString()));
        }

    }
}
