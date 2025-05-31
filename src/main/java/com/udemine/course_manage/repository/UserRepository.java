package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByname(String username);
    boolean existsByemail(String email);
}
