package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.service.reposervices.TBUserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendCommand implements MyBotCommand {
    final BotConfig botConfig;
    final TBUserService tbUserService;


    @Override
    public boolean checkMessage(String msg) {
        return msg.startsWith("/send");
    }

    @Override
    public List<SendMessage> process(Update update) {
        String textMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Long owner = Long.valueOf(botConfig.getOwner());
        List<SendMessage> sendMessageList = new ArrayList<>();

        if (chatId.equals(owner)) {
            var textToSend = EmojiParser.parseToUnicode(textMessage.substring("/send".length()));
            var users = tbUserService.getAllTBUsers();

            for (TBUser user : users) {
                sendMessageList.add(SendMessage.builder()
                        .chatId(user.getChatId())
                        .text(textToSend)
                        .build());
            }
            return sendMessageList;

        }

        sendMessageList.add(SendMessage.builder()
                .chatId(chatId)
                .text("Command was not recognized")
                .build());
        return sendMessageList;
    }
}

