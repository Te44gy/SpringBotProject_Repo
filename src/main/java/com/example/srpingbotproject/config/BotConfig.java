package com.example.srpingbotproject.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@Data
@Slf4j
@Validated
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
