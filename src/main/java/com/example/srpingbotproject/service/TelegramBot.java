package com.example.srpingbotproject.service;

import com.example.srpingbotproject.buttons.MyButtons;
import com.example.srpingbotproject.buttons.NoButton;
import com.example.srpingbotproject.buttons.YesButton;
import com.example.srpingbotproject.commands.*;
import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.Ads;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.AdsRepository;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
//@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final TBUserRepository tbUserRepository;
    private final AdsRepository adsRepository;
    private final BotConfig botConfig;
    private final KeyboardMaker keyboardMaker;
    private final MessageService messageService;


    public TelegramBot(TBUserRepository tbUserRepository, AdsRepository adsRepository, BotConfig botConfig, MessageService messageService, KeyboardMaker keyboardMaker){

        this.tbUserRepository = tbUserRepository;
        this.adsRepository = adsRepository;
        this.botConfig = botConfig;
        this.keyboardMaker = keyboardMaker;
        this.messageService = messageService;


        List<BotCommand> listOfCommands = new ArrayList<>();     //лист содержащий лист команд
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));          //это команда есть и она добавится в список
//        listOfCommands.add(new BotCommand("/myData", "get user data"));                                 //команды которые не прописаны видимо добавить нельзя
        listOfCommands.add(new BotCommand("/help", "get bot info about using"));
        listOfCommands.add(new BotCommand("/YesNo","yes and no buttons"));              //? Команды не выставляются

        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException tae) {
            log.info("Ошибка выставления команд в бота: "+ tae.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }
    @Override
    public String getBotToken() {
        return botConfig.getToken();           //что делает этот токен и как конкретно происходит подключение программы к боту?
    }
 
    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


                List<MyBotCommand> listOfCommands = Arrays.asList(new StartCommand(tbUserRepository )
                        , new HelpCommand()
                        , new SendCommand(botConfig, tbUserRepository, this)
                        , new YesNoCommand(keyboardMaker));
                var messageToSend = listOfCommands.stream()
                        .filter(m -> m.checkMessage(messageText))
                        .findFirst()
                        .map(m -> m.process(update))
                        .orElseGet(() ->  SendMessage.builder().text("Command was not recognized").chatId(chatId).build());
//                var messageToSendWithKeyboardMarkup = keyboardMaker.addReplyKeyboardMarkupToMessage(messageToSend);
//                executeMessage(messageToSendWithKeyboardMarkup);    //? Сделал дебагин и написано, что клавиатура выставлена, но почему-то не отображается в чате
                                                                      //? Криво работает, теперь оно сработало, но при этом не вылазят кнопки Yes, No
                                                                      //? Почему метод создания меню сетки даже не используется, но сетка сохранена в чате???
                    executeMessage(messageToSend);

        } else if
        (update.hasCallbackQuery()) {                          //Проверяем может в update передался какой-то айди кнопки ане текст сообщения
            List<MyButtons> buttonsList = Arrays.asList(new YesButton(messageService), new NoButton(messageService));
            String buttonId = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            var messageToSend = buttonsList.stream()
                    .filter(cb -> cb.checkButton(buttonId))
                    .findFirst()
                    .map(cb -> cb.process(update))
                    .orElseGet(() -> EditMessageText.builder().text("Command was not recognized").chatId(chatId).build());
            executeEditMessage(messageToSend);
        }
    }

    private void executeEditMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        }
        catch (TelegramApiException tae){
            log.info("Произошла ошибка при отправке сообщения "+ tae.getMessage());
        }
    }

    public void executeMessage(SendMessage message){
        try{
            execute(message);
        }
        catch (TelegramApiException tae){
            log.info("Произошла ошибка при отправке сообщения "+ tae.getMessage());
        }
    }

//    @Scheduled(cron = "* * * * * *") //Когда будет вызываться метод, в этом случай он будет включиться каждую секунду
    //Звезды отображают время: секунды, минуты, часы, дата(день), месяц, день недели(например каждого 4-го числа если это понедельник)
    @Scheduled(cron = "* * * * * *")   //В этом случае в каждую 0 секунду, то есть каждую минуту
    private void sendAds(){

        var ads = adsRepository.findAll();
        var users = tbUserRepository.findAll();
        log.info("Попытка отправки");

        for(Ads ad:ads){
            for(TBUser user:users){
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(user.getChatId()));
                message.setText(ad.getAd());
                executeMessage(message);
            }
        }
    }
}
