package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    // Additional query methods can be defined here if needed

}
