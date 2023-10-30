package com.example.srpingbotproject.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.srpingbotproject.buttons.NoButton.NO_BUTTON;
import static com.example.srpingbotproject.buttons.YesButton.YES_BUTTON;

@Service
public class KeyboardMaker {


    public SendMessage addInlineKeyboardMarkupToMessage(SendMessage sendMessage){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();     //Класс для создания кнопок под сообщением
        List<List<InlineKeyboardButton>> rowsInLIne = new ArrayList<>();      //Именно такой лист нужно дать в параметр ".setKeyboard метода"
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);                 //Это идентификатор, который помогает боту понять какая кнопка была нажата
        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLIne.add(rowInLine);

        keyboardMarkup.setKeyboard(rowsInLIne);
        sendMessage.setReplyMarkup(keyboardMarkup);          //Выставить разметку кнопок для сообщения
        return sendMessage;
    }


    public SendMessage addReplyKeyboardMarkupToMessage(SendMessage sendMessage) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();                //Класс для настройки разметки клавиатуры

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/start");
        row.add("/help");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
