package com.example.srpingbotproject.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@Data
@Validated
@PropertySource("application.properties")  // Откуда брать информацию/ресурсы
//@ConfigurationProperties("bot")
public class BotConfig {


    @NotBlank
       // Это поле будет равняться значению которое прописано в "application.properties"
    @Value("${bot.name}")
    String botName;

    @NotBlank
    @Value("${bot.token}")  // Это поле будет равняться значению которое прописано в "application.properties"
    String botToken;

    @NotBlank
    @Value("${bot.owner}")
    String botOwner;

}
