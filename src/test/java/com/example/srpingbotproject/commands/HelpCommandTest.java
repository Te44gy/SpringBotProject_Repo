package com.example.srpingbotproject.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.srpingbotproject.commands.HelpCommand.HELP_TEXT;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {

    @InjectMocks
    HelpCommand helpCommand;

    @Test
    void processTest(){

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(1276509851L);
        message.setChat(chat);
        update.setMessage(message);

        List<SendMessage> ansToCheck = helpCommand.process(update);

        List<SendMessage> expectedAns = List.of(SendMessage.builder()
                .text(HELP_TEXT)
                .chatId(1276509851L)
                .build());

        Assertions.assertEquals(expectedAns, ansToCheck);
    }
}
