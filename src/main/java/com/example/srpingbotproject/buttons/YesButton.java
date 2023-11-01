package com.example.srpingbotproject.buttons;

import com.example.srpingbotproject.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class YesButton implements MyButtons{
    public static final String YES_BUTTON = "YES_BUTTON";


    final MessageService messageService;


    @Override
    public boolean checkButton(String buttonId) {
        return buttonId.equals(YES_BUTTON);
    }

    @Override
    public EditMessageText process(Update update) {
        return messageService.editMassage("You pressed YES button", update);
    }
}
