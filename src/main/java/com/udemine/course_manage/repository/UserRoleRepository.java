package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    boolean existsByUserIdAndRoleId(int userId, int roleId);
}
