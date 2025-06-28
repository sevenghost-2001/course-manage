package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Integer> {
    boolean existsByTitle(String title);
    boolean existsByPosition(int position);
}
