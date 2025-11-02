package com.udemine.course_manage.repository;

import com.udemine.course_manage.entity.PasswordHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    @Query("""
       select p.passwordHash
       from PasswordHistory p
       where p.userId = :userId
       order by p.createdAt desc
    """)
    List<String> findHashes(@Param("userId") Integer userId, Pageable pageable);

    @Modifying
    @Query(value = """
    DELETE FROM password_history
    WHERE user_id = :userId
      AND id NOT IN (
        SELECT id FROM (
          SELECT id
          FROM password_history
          WHERE user_id = :userId
          ORDER BY created_at DESC
          LIMIT 5
        ) AS temp
      )
    """, nativeQuery = true)
    void trimToFive(@Param("userId") Integer userId);

}
