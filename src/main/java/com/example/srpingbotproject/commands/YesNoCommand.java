package com.example.srpingbotproject.commands;

import com.example.srpingbotproject.model.CustomButton;
import com.example.srpingbotproject.service.KeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.srpingbotproject.buttons.NoButton.NO_BUTTON;
import static com.example.srpingbotproject.buttons.YesButton.YES_BUTTON;

@Component
@RequiredArgsConstructor
public class YesNoCommand implements MyBotCommand {

    final KeyboardMaker keyboardMaker;


    @Override
    public boolean checkMessage(String msg) {
        return msg.equals("/YesNo");
    }

    @Override
    public List<SendMessage> process(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage message = SendMessage.builder().chatId(chatId).text("Are u sure?").build();
        List<CustomButton> listOfButtons = List.of(CustomButton.builder()
                .text("Yes")
                .callbackData(YES_BUTTON)
                .build(), CustomButton.builder()
                .text("No")
                .callbackData(NO_BUTTON)
                .build());
        var keyboard = keyboardMaker.addInlineKeyboardMarkupToMessage(listOfButtons);
        message.setReplyMarkup(keyboard);
        return List.of(message);
    }
}
