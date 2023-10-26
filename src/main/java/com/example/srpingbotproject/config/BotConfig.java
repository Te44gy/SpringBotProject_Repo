package com.example.srpingbotproject.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@Data
//@Validated
//@PropertySource("application.properties")  // Откуда брать информацию/ресурсы
@ConfigurationProperties(prefix = "bot")
public class BotConfig{


    @NotBlank
       // Это поле будет равняться значению которое прописано в "application.properties"
//    @Value("${bot.name}")
    private String name;

    @NotBlank
//    @Value("${bot.token}")  // Это поле будет равняться значению которое прописано в "application.properties"
    private String token;

    @NotBlank
//    @Value("${bot.owner}")
    private String owner;

}
