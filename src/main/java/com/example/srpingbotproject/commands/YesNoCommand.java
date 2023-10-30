package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.service.KeyboardMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class YesNoCommand implements MyBotCommand {

    final KeyboardMaker keyboardMaker;

    public YesNoCommand(KeyboardMaker keyboardMaker) {
        this.keyboardMaker = keyboardMaker;
    }

    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/YesNo");
    }

    @Override
    public SendMessage process(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Are u sure?");
        return keyboardMaker.addInlineKeyboardMarkupToMessage(message);
    }

}
