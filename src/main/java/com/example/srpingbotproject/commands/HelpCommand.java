package com.example.srpingbotproject.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class HelpCommand implements MyBotCommand {
    public static final String HELP_TEXT = "This bot is created to demonstrated Spring capabilities\n\n"+
            "press menu to see all available commands";

    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/help");
    }

    @Override
    public List<SendMessage> process(Update update) {
        Long chatId = update.getMessage().getChatId();
        return List.of(SendMessage.builder()
                .text(HELP_TEXT)
                .chatId(chatId)
                .build());
    }
}
