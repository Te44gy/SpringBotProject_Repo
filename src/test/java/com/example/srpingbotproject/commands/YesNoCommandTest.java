package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.model.CustomButton;
import com.example.srpingbotproject.service.KeyboardMaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.example.srpingbotproject.buttons.NoButton.NO_BUTTON;
import static com.example.srpingbotproject.buttons.YesButton.YES_BUTTON;


@ExtendWith(MockitoExtension.class)
public class YesNoCommandTest {

    @InjectMocks
    YesNoCommand yesNoCommand;
    @Mock
    KeyboardMaker keyboardMaker;

    @Test
    void ProcessTest(){

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1276509851L);
        message.setChat(chat);
        update.setMessage(message);
        java.util.List<CustomButton> YesNoListOfButtons = java.util.List.of(CustomButton.builder()
                .text("Yes")
                .callbackData(YES_BUTTON)
                .build(), CustomButton.builder()
                .text("No")
                .callbackData(NO_BUTTON)
                .build());

        InlineKeyboardButton inlineKeyboardButton1 = InlineKeyboardButton.builder().text("Yes") .callbackData(YES_BUTTON).build();
        InlineKeyboardButton inlineKeyboardButton2 = InlineKeyboardButton.builder().text("No")  .callbackData(NO_BUTTON) .build();

        List<InlineKeyboardButton> row1 = List.of(inlineKeyboardButton1, inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rows = List.of(row1);
        InlineKeyboardMarkup YesNoMarkupInLine = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        Mockito.when(keyboardMaker.addInlineKeyboardMarkupToMessage(YesNoListOfButtons)).thenReturn(YesNoMarkupInLine);
        //ansToCheck
        var ansToCheck = yesNoCommand.process(update);




        SendMessage ansMessage = SendMessage.builder().chatId(1276509851L).text("Are u sure?").build();
        ansMessage.setReplyMarkup(YesNoMarkupInLine);
        //expectedAns
        var expectedAns = List.of(ansMessage);

        Assertions.assertEquals(expectedAns, ansToCheck);




    }
}
