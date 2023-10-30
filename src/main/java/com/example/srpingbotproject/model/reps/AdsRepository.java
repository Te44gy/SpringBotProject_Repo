package com.example.srpingbotproject.model.reps;

import com.example.srpingbotproject.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface AdsRepository extends JpaRepository<Ads,Long> {
}
