package com.example.srpingbotproject.service;

import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.Ads;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.AdsRepository;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
//@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final TBUserRepository tbUserRepository;
    private final AdsRepository adsRepository;
    private final BotConfig botConfig;

    static final String HELP_TEXT = "This bot is created to demonstrated Spring capabilities\n\n"+
            "press menu to see all available commands";
    static final String NO_BUTTON = "NO_BUTTON";
    static final String YES_BUTTON = "YES_BUTTON";


    public TelegramBot(TBUserRepository tbUserRepository, AdsRepository adsRepository, BotConfig botConfig){

        this.tbUserRepository = tbUserRepository;
        this.adsRepository = adsRepository;
        this.botConfig = botConfig;


        List<BotCommand> listOfCommands = new ArrayList<>();     //лист содержащий лист команд
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));          //это команда есть и она добавится в список
//        listOfCommands.add(new BotCommand("/myData", "get user data"));                                 //команды которые не прописаны видимо добавить нельзя
//        listOfCommands.add(new BotCommand("/deleteData", "delete user data"));
        listOfCommands.add(new BotCommand("/help", "get bot info about using"));
        listOfCommands.add(new BotCommand("/register","register"));

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
        return botConfig.getToken(); //что делает этот токен и как конкретно происходит подключение программы к боту?
    }


    public Long getBotOwner(){
         return Long.valueOf(botConfig.getOwner());
    }
 
    @Override
    public void onUpdateReceived(Update update) {                   // Пришедшее сообщения

        if(update.hasMessage() && update.getMessage().hasText()){   //Убеждаемся что в пришедшем сообщении есть текст
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();          //Что бы что-то отправить мы должны знать чат айди на который нужно отправлять

            if(messageText.contains("/send") && chatId==getBotOwner()){
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(6));
                System.out.println(textToSend);
                var users = tbUserRepository.findAll();
                for (TBUser user: users){
                    sendMessage(user.getChatId(), textToSend);
                }

            } else {
                switch (messageText){

                    case "/start": startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        registerUser(update.getMessage());
                        break;

                    case "/help": sendMessage(chatId, HELP_TEXT);
                        break;

                    case "/register":

                        register(chatId);
                        break;

                    default:
                        sendMessage(chatId, "Command was not recognized");
                }

            }



        } else if (update.hasCallbackQuery()) {  //Проверяем может в update передался какой-то айди кнопки ане текст сообщения
            String callBackData = update.getCallbackQuery().getData();                 // Получай айди команды
            long messageId = update.getCallbackQuery().getMessage().getMessageId();    // Получить айди сообщения, а не чата для редактирования сообщения при нажатии кнопок
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if(callBackData.equals(YES_BUTTON)){
                executeEditMessageText("You pressed YES button", chatId, messageId);

            }
            else if(callBackData.equals(NO_BUTTON)){
                executeEditMessageText("You pressed NO button", chatId, messageId);
            }
        }

    }

    private void register(long chatId) {
        SendMessage message = new SendMessage();      // Специальный класс для отправки сообщений из библиотеки телеграмботс
        message.setChatId(String.valueOf(chatId));
        message.setText("Are u sure?");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();     //Класс для создания кнопок под сообщением
        List<List<InlineKeyboardButton>> rowsInLIne = new ArrayList<>();      //Именно такой лист нужно дать в параметр ".setKeyboard метода"
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);        //Это идентификатор, который помогает боту понять какая кнопка была нажата
        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLIne.add(rowInLine);

        keyboardMarkup.setKeyboard(rowsInLIne);
        message.setReplyMarkup(keyboardMarkup);         //Выставить разметку кнопок для сообщения
        executeMessage(message);



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

    private void startCommandReceived(long chatId, String firstName){   // Вызываем метод когда поступила команда о старте работы

        String answer = EmojiParser.parseToUnicode("Hi, " + firstName + ", nice to meet you " +":jack_o_lantern:");    // Класс для вставки эмоций
        sendMessage(chatId, answer);                                    // Вызываем метод отправки сообщения

    }

    private void sendMessage(long chatId, String textToSend) {          // Метод отправки сообщения
        SendMessage message = new SendMessage();                        // Специальный класс для отправки сообщений из библиотеки телеграмботс
        message.setChatId(String.valueOf(chatId));                      // Телеграм почему-то хочет что бы мы присваивали If исходящему сообщению в String
        message.setText(textToSend);                                    // Сообщение, которое хотим отправить

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(); //Класс для настройки разметки клавиатуры

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("weather");
        row.add("joke");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeMessage(message);


    }


    private void executeEditMessageText(String text, long chatId, long messageId){
        EditMessageText message = new EditMessageText();       //Класс позволяющий редактировать сообщение зная его айди
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int)messageId);                  //Говорим что редактировать нужно конкретное сообщение
        try{
            execute(message);
        }
        catch (TelegramApiException tae){
            log.info("Произошла ошибка при отправке сообщения "+ tae.getMessage());
        }


    }

    private void executeMessage(SendMessage message){
        try{                                                   // Используем tryCatch при отправке потому что, что-то может пойти не так
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

    public boolean methodForTestsOnly(){
        return tbUserRepository.findById(1276509851L).isPresent();
    }
}
