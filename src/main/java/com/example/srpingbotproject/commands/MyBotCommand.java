package com.example.srpingbotproject.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface MyBotCommand {
    boolean checkMessage(String msg);
    List<SendMessage> process(Update update);
}
