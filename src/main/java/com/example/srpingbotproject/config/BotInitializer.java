//package com.example.srpingbotproject.config;
//
//import com.example.srpingbotproject.service.TelegramBot;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//@Component
//@Slf4j
//public class BotInitializer {
//
//    final
//    TelegramBot telegramBot;
//    public BotInitializer(TelegramBot telegramBot) {
//        this.telegramBot = telegramBot;
//    }
//
//    @EventListener(value = {ContextRefreshedEvent.class})                                    // это способ зарегистрировать обработчик события
//    public void init(){
//        try{
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);  // посредник между приложением и сервером
//            telegramBotsApi.registerBot(telegramBot);
//        }
//        catch (TelegramApiException tae){
//            System.out.println("Ошибка регистрации телеграм бота " + tae.getMessage());
//        }
//        log.info("Бот инициализирован");
//    }
//        @PostConstruct
//    private void setCommandsListToBot(){
//            telegramBot.setCommandList(); //Вопрос: вот же я выставляю команды в боте после его инициализации, почему команды всё ровно не выставляются?
//    }
//
//}
