package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.service.reposervices.TBUserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements MyBotCommand {

    final TBUserService tbUserService;

    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/start");
    }

    @Override
    public List<SendMessage> process(Update update) {
        String firstName = update.getMessage().getChat().getFirstName();
        Long chatId = update.getMessage().getChatId();
        String textMessage = EmojiParser.parseToUnicode("Hi, " + firstName + ", nice to meet you " +":jack_o_lantern:");
        tbUserService.registerTBUser(update);
        return List.of(SendMessage.builder()
                .chatId(chatId)
                .text(textMessage)
                .build());

    }

}
