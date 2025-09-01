package com.mt_analytics.mt_analytics.repository;

import com.mt_analytics.mt_analytics.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    Optional<Authentication> findByEmail(String username);
}
