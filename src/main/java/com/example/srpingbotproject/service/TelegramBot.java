package com.example.srpingbotproject.service;

import com.example.srpingbotproject.buttons.MyButtons;
import com.example.srpingbotproject.commands.MyBotCommand;
import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.Ads;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.AdsRepository;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final TBUserRepository tbUserRepository;
    private final AdsRepository adsRepository;
    private final BotConfig botConfig;
    private final List<MyBotCommand> myBotCommandList;
    private final List<MyButtons> myButtonsList;

    @EventListener(value = {ContextRefreshedEvent.class})
    // это способ зарегистрировать обработчик события
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);

        } catch (TelegramApiException tae) {
            System.out.println("Ошибка регистрации телеграм бота " + tae.getMessage());
        }
    }

    @PostConstruct
    private void setCommands() {
        try {
            SetMyCommands commands = SetMyCommands.builder()
                    .clearCommands()
                    .command(BotCommand.builder()
                            .command("/start")
                            .description("get a welcome message")
                            .build())
                    .command(BotCommand.builder()
                            .command("/help")
                            .description("get bot info about using")
                            .build())
                    .command(BotCommand.builder()
                            .command("/YesNo")
                            .description("yes and no buttons")
                            .build())
                    .build();
            execute(commands);                           //todo вопрос: команды всё ровно не вставляются


        } catch (TelegramApiException tae) {
            System.out.println("Ошибка вставки команд в бота " + tae.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            var messagesToSend = myBotCommandList.stream()
                    .filter(m -> m.checkMessage(messageText))
                    .findFirst()
                    .map(m -> m.process(update))
                    .orElseGet(() -> List.of(SendMessage.builder().text("Command was not recognized").chatId(chatId).build()));
            for (SendMessage messageToSend : messagesToSend) {
                log.info(messageToSend.getText());
                executeMessage(messageToSend);
            }

        } else if
        (update.hasCallbackQuery()) {                          //Проверяем может в update передался какой-то айди кнопки, а не текст сообщения
            String buttonId = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            log.info(buttonId);
            var messageToSend = myButtonsList.stream()
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
        } catch (TelegramApiException tae) {
            log.info("Произошла ошибка при отправке сообщения " + tae.getMessage());
        }
    }

    public void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException tae) {
            log.info("Произошла ошибка при отправке сообщения " + tae.getMessage());
        }
    }

    //    @Scheduled(cron = "* * * * * *") //Когда будет вызываться метод, в этом случай он будет включиться каждую секунду
    //Звезды отображают время: секунды, минуты, часы, дата(день), месяц, день недели(например каждого 4-го числа если это понедельник)
    @Scheduled(cron = "* * * * * *")
    private void sendAds() {

        var ads = adsRepository.findAll();
        var users = tbUserRepository.findAll();
        log.info("Попытка отправки");

        for (Ads ad : ads) {
            for (TBUser user : users) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(user.getChatId()));
                message.setText(ad.getAd());
                executeMessage(message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
