package com.example.srpingbotproject.service;

import com.example.srpingbotproject.model.CustomButton;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.srpingbotproject.buttons.NoButton.NO_BUTTON;
import static com.example.srpingbotproject.buttons.YesButton.YES_BUTTON;

//@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class KeyboardMakerTest {

    @InjectMocks
    private KeyboardMaker keyboardMaker;

    @Test
    void addInlineKeyboardMarkupToMessageTest2Buttons() {
        List<CustomButton> listOfButtons = List.of(
                  CustomButton.builder().text("Yes") .callbackData(YES_BUTTON).build()
                , CustomButton.builder().text("No")  .callbackData(NO_BUTTON) .build());
        InlineKeyboardMarkup ansToCheck = keyboardMaker.addInlineKeyboardMarkupToMessage(listOfButtons);


        InlineKeyboardButton inlineKeyboardButton1 = InlineKeyboardButton.builder().text("Yes") .callbackData(YES_BUTTON).build();
        InlineKeyboardButton inlineKeyboardButton2 = InlineKeyboardButton.builder().text("No")  .callbackData(NO_BUTTON) .build();

        List<InlineKeyboardButton> row1 = List.of(inlineKeyboardButton1, inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rows = List.of(row1);
        InlineKeyboardMarkup expectedMarkupInLine = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        Assertions.assertEquals(expectedMarkupInLine, ansToCheck);

    }

    @Test
    void addInlineKeyboardMarkupToMessageTest3Buttons() {
        List<CustomButton> listOfButtons = List.of(
                CustomButton.builder().text("Yes") .callbackData(YES_BUTTON).build()
                , CustomButton.builder().text("No")  .callbackData(NO_BUTTON) .build()
                , CustomButton.builder().text("test").callbackData("test")    .build());
        InlineKeyboardMarkup ansToCheck = keyboardMaker.addInlineKeyboardMarkupToMessage(listOfButtons);


        InlineKeyboardButton inlineKeyboardButton1 = InlineKeyboardButton.builder().text("Yes") .callbackData(YES_BUTTON).build();
        InlineKeyboardButton inlineKeyboardButton2 = InlineKeyboardButton.builder().text("No")  .callbackData(NO_BUTTON) .build();
        InlineKeyboardButton inlineKeyboardButton3 = InlineKeyboardButton.builder().text("test").callbackData("test")    .build();

        List<InlineKeyboardButton> row1 = List.of(inlineKeyboardButton1, inlineKeyboardButton2);
        List<InlineKeyboardButton> row2 = List.of(inlineKeyboardButton3);

        List<List<InlineKeyboardButton>> rows = List.of(row1,row2);
        InlineKeyboardMarkup expectedMarkupInLine = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        Assertions.assertEquals(expectedMarkupInLine, ansToCheck);

    }
}
