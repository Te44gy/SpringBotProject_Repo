package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import com.example.srpingbotproject.service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class SendCommand implements MyBotCommand {
    final BotConfig botConfig;
    final TBUserRepository tbUserRepository;
    final TelegramBot telegramBot;

    public SendCommand(BotConfig botConfig, TBUserRepository tbUserRepository, TelegramBot telegramBot) {
        this.botConfig = botConfig;
        this.tbUserRepository = tbUserRepository;
        this.telegramBot = telegramBot;
    }


    @Override
    public boolean checkMessage(String msg) {
        return msg.startsWith("/send");
    }

    @Override
    public SendMessage process(Update update) {
        String textMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Long owner = Long.valueOf(botConfig.getOwner());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (chatId.equals(owner)) {
            var textToSend = EmojiParser.parseToUnicode(textMessage.substring(5));
            var users = tbUserRepository.findAll();
            for (TBUser user : users) {
                message.setText(textToSend);
                message.setChatId(user.getChatId());
                telegramBot.executeMessage(message);
            }
            message.setText("Message has been send");
            return message;

        }
        message.setText("Command was not recognized");
        return message;
    }
}

