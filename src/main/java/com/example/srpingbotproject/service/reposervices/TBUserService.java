package com.example.srpingbotproject.service.reposervices;

import com.example.srpingbotproject.model.TBUser;
import com.example.srpingbotproject.model.reps.TBUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class TBUserService {

    final TBUserRepository tbUserRepository;

    public TBUserService(TBUserRepository tbUserRepository) {
        this.tbUserRepository = tbUserRepository;
    }

    public void registerTBUser(Update update) {
        if(tbUserRepository.findById(update.getMessage().getChatId()).isEmpty()){
            Long chatId = update.getMessage().getChatId();
            var chat = update.getMessage().getChat();
            TBUser userToSave = TBUser.builder()
                    .chatId(chatId)
                    .firstName(chat.getFirstName())
                    .lastName(chat.getLastName())
                    .userName(chat.getUserName())
                    .registerDate(new Timestamp(System.currentTimeMillis()))
                    .build();

            tbUserRepository.save(userToSave);
            log.info("пользователь сохранен: "+ userToSave);

        }
    }

    public List<TBUser> getAllTBUsers(){
        return tbUserRepository.findAll();
    }
}
