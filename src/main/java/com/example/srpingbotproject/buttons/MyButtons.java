package com.example.srpingbotproject.buttons;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface MyButtons {

    public boolean checkButton(String buttonId);
    public  EditMessageText process(Update update);
    }

