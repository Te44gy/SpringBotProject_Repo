package com.example.srpingbotproject.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageService {
    public EditMessageText editMassage(String textMassage ,Update update){
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setText(textMassage);
        message.setMessageId((int)messageId);
        return message;
    }
}
