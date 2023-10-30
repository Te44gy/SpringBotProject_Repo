package com.example.srpingbotproject.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements MyBotCommand {
    static final String HELP_TEXT = "This bot is created to demonstrated Spring capabilities\n\n"+
            "press menu to see all available commands";

    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/help");
    }

    @Override
    public SendMessage process(Update update) {
        SendMessage message = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        message.setText(HELP_TEXT);
        message.setChatId(String.valueOf(chatId));
        return message;
    }
}
