//package com.example.srpingbotproject.service;
//
//import com.example.srpingbotproject.model.Ads;
//import com.example.srpingbotproject.model.TBUser;
//import com.example.srpingbotproject.model.reps.AdsRepository;
//import com.example.srpingbotproject.model.reps.TBUserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//
//@Service
//@Slf4j
//public class AdsService {
//
//    final AdsRepository adsRepository;
//    final TBUserRepository tbUserRepository;
//    final TelegramBot telegramBot;
//
//    public AdsService(AdsRepository adsRepository, TBUserRepository tbUserRepository, TelegramBot telegramBot) {
//        this.adsRepository = adsRepository;
//        this.tbUserRepository = tbUserRepository;
//        this.telegramBot = telegramBot;
//    }
//
//    private void sendAds(){
//
//        var ads = adsRepository.findAll();
//        var users = tbUserRepository.findAll();
//        log.info("Попытка отправки");
//
//        for(Ads ad:ads){
//            for(TBUser user:users){
//                SendMessage message = new SendMessage();
//                message.setChatId(String.valueOf(user.getChatId()));
//                message.setText(ad.getAd());
//                telegramBot.executeMessage(message);
//            }
//        }
//    }
//}
