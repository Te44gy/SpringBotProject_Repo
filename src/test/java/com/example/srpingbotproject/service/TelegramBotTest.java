package com.example.srpingbotproject.service;

import com.example.srpingbotproject.config.BotConfig;
import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.AdsRepository;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ContextConfiguration
@TestPropertySource ("application.properties")  //как использовать?
@ExtendWith(MockitoExtension.class)   // Почему таким необычным образом?
public class TelegramBotTest{

 @InjectMocks                         // А тут мы говорим что это тот класс в который и "заинжекшиный" класс который мы пометили за Mock
 private TelegramBot telegramBot;
 @Mock                                // Класс который внедрен спрингом в telegramBot
 private TBUserRepository tbUserRepository;
 @Mock
 private AdsRepository adsRepository;
 @Mock
 private BotConfig botConfig;




    @Test
     void methodForTestsOnlyTest(){
        Optional<Long> optionalLong = Optional.of(1276509851L);
        Optional<TBUser> optionalTBUSer = Optional.of
                ( new TBUser (1276509851L,"Martin","Gofman","Te4gy", new Timestamp(System.currentTimeMillis())));
        //Когда(when) будет вызван метод на классе который
        //мы "мокнули" и тогда вернется(then) то что мы запишем
//        Mockito.when(botConfig.getOwner()).thenReturn();
//        Mockito.when(telegramBot.getBotOwner()).thenReturn(1276509851L);
//        Mockito.when(tbUserRepository.findById(1276509851L)).thenReturn(optionalTBUSer);
//        boolean  ans = telegramBot.methodForTestsOnly();



    }
}

