package com.example.srpingbotproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="adsTable")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ad;

}
