package com.example.srpingbotproject.service;

import com.example.srpingbotproject.model.CustomButton;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardMaker {

    static final int NUM_COLUMNS = 2;

    public InlineKeyboardMarkup addInlineKeyboardMarkupToMessage(List<CustomButton> buttonList){
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        var numRows = (int) Math.ceil((double) buttonList.size()/NUM_COLUMNS);  //округляет вверх

        for(int row=0; row < numRows; row++){
            int start = row*NUM_COLUMNS;
            int end = Math.min(start+NUM_COLUMNS, buttonList.size());

            List<InlineKeyboardButton> currentRow = new ArrayList<>();

            for(int j = start; j<end; j++){
                CustomButton buttonFromList = buttonList.get(j);
                InlineKeyboardButton buttonOnKeyBoard = InlineKeyboardButton.builder()
                        .text(buttonFromList.getText())
                        .callbackData(buttonFromList.getCallbackData())
                        .build();
                currentRow.add(buttonOnKeyBoard);
            }
            rows.add(currentRow);
        }
        markupInLine.setKeyboard(rows);

    return markupInLine;
    }
}
