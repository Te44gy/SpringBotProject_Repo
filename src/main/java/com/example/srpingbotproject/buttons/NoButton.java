package com.example.srpingbotproject.buttons;

import com.example.srpingbotproject.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class NoButton implements MyButtons{
    public static final String NO_BUTTON = "NO_BUTTON";


    final MessageService messageService;

    @Override
    public boolean checkButton(String buttonId) {
        return buttonId.equals(NO_BUTTON);
    }

    @Override
    public EditMessageText process(Update update) {
        return messageService.editMassage("You pressed NO button", update);
    }
}

