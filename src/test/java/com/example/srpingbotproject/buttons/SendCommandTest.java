package com.example.srpingbotproject.buttons;

import com.example.srpingbotproject.commands.SendCommand;
import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.service.reposervices.TBUserService;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SendCommandTest {

    @InjectMocks
    SendCommand sendCommand;

    @Mock
    TBUserService tbUserService;
    @Mock
    BotConfig botConfig;



    @Test
    void processTestWithOneUser() {
        String textMessage = "/send Проверка метода send";
        Long ownerId = 1276509851L;

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(ownerId);
        message.setChat(chat);
        message.setText(textMessage);
        update.setMessage(message);

        Mockito.when(botConfig.getOwner()).thenReturn(String.valueOf(ownerId));
        Mockito.when(tbUserService.getAllTBUsers()).thenReturn(List.of(
                TBUser.builder().chatId(ownerId).build()));
        List<SendMessage> listToCheck = sendCommand.process(update);

        List<SendMessage> expectedList = List.of(SendMessage.builder()
                .text(textMessage.substring("/send".length()))
                .chatId(1276509851L)
                .build());

        Assertions.assertEquals(expectedList, listToCheck);

    }

    @Test
    void processTestWithTwoUsers(){
        String textMessage = "/send Проверка метода send";
        Long ownerId = 1276509851L;
        Long notOwnerId = 1258394728L;

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(ownerId);
        message.setChat(chat);
        message.setText(textMessage);
        update.setMessage(message);

        Mockito.when(botConfig.getOwner()).thenReturn(String.valueOf(ownerId));
        Mockito.when(tbUserService.getAllTBUsers()).thenReturn(List.of(
                TBUser.builder().chatId(ownerId)    .build()
               ,TBUser.builder().chatId(notOwnerId) .build()));
        List<SendMessage> listToCheck = sendCommand.process(update);

        List<SendMessage> expectedList = List.of(
                SendMessage.builder()
                .text(textMessage.substring("/send".length()))
                .chatId(1276509851L)
                .build()
                , SendMessage.builder()
                .text(textMessage.substring("/send".length()))
                .chatId(1258394728L)
                .build());

        Assertions.assertEquals(expectedList, listToCheck);
    }

    @Test
    void processTestWithWrongOwnerId(){
        String textMessage = "/send Проверка метода send";
        Long ownerId = 1276509851L;
        Long notOwnerId = 1258394728L;

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(notOwnerId);
        message.setChat(chat);
        message.setText(textMessage);
        update.setMessage(message);

        Mockito.when(botConfig.getOwner()).thenReturn(String.valueOf(ownerId));
//        Mockito.when(tbUserService.getAllTBUsers()).thenReturn(List.of(        выдается ошибка потому что в данном сценарии
//                TBUser.builder().chatId(ownerId)    .build()                   tbUserService.getAllTBUsers() не будет задействован
//                ,TBUser.builder().chatId(notOwnerId).build()));                и в итоге его не нужно обрабатывать
        List<SendMessage> listToCheck = sendCommand.process(update);

        List<SendMessage> expectedList = List.of(
                SendMessage.builder()
                        .text("Command was not recognized")
                        .chatId(notOwnerId)
                        .build());

        Assertions.assertEquals(expectedList, listToCheck);

    }
}
