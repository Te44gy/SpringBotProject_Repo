package com.example.srpingbotproject.config;

import com.example.srpingbotproject.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    @Autowired
    TelegramBot telegramBot; // инициализируем бота

    @EventListener(value = {ContextRefreshedEvent.class})           // это способ зарегистрировать обработчик события
    public void init(){
          //??? посредник между приложением и сервером
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        }
        catch (TelegramApiException tae){
            System.out.println("Ошибка регистрации телеграм бота "+ tae.getMessage() );
        }
    }
}
