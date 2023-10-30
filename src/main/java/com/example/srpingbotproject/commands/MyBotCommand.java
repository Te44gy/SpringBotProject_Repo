package com.example.srpingbotproject.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MyBotCommand {
    boolean checkMessage(String msg);
    SendMessage process(Update update);
}
