package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
