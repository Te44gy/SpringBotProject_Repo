package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;

@Slf4j
@Component
public class StartCommand implements MyBotCommand {

    final
    TBUserRepository tbUserRepository;

    public StartCommand(TBUserRepository tbUserRepository) {
        this.tbUserRepository = tbUserRepository;
    }

    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/start");
    }

    @Override
    public  SendMessage process(Update update) {
        String firstName = update.getMessage().getChat().getFirstName();
        Long chatId = update.getMessage().getChatId();
        String textMessage = EmojiParser.parseToUnicode("Hi, " + firstName + ", nice to meet you " +":jack_o_lantern:");
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);
        registerUser(update.getMessage());
        return message;

    }

    private void registerUser(Message message) {
        if(tbUserRepository.findById(message.getChatId()).isEmpty()){
            Long chatId = message.getChatId();
            var chat = message.getChat();
            TBUser user = new TBUser();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisterDate(new Timestamp(System.currentTimeMillis()));

            tbUserRepository.save(user);
            log.info("пользователь сохранен: "+user);

        }
    }
}
