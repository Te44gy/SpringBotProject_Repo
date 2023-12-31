package com.example.srpingbotproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name="TelegramBotUsers")
public class TBUser {

    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private Timestamp registerDate;

    public TBUser() {

    }
}
