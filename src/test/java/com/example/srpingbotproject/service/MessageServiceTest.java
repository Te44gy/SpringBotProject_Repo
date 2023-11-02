package com.example.srpingbotproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    MessageService messageService;

    @Test
    void editMassageTest(){
        Update ourUpdate = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1276509851L);
        message.setMessageId(532);
        message.setChat(chat);
        callbackQuery.setMessage(message);
        ourUpdate.setCallbackQuery(callbackQuery);

        var editMessageToTest = messageService.editMassage("Текст который заменен при редактировании" , ourUpdate);


        var expectedAns = EditMessageText.builder()
                .text("Текст который заменен при редактировании")
                .chatId(1276509851L)
                .messageId(532)
                .build();

        Assertions.assertEquals(expectedAns, editMessageToTest);
    }
}
