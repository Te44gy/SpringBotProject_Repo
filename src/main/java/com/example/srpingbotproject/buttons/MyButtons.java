package com.example.srpingbotproject.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MyButtons {
    boolean checkButton(String buttonId);
    EditMessageText process(Update update);
}
