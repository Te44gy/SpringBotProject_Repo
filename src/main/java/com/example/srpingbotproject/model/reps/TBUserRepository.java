package com.example.srpingbotproject.model.reps;

import com.example.srpingbotproject.model.TBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TBUserRepository extends JpaRepository<TBUser, Long> {
}
