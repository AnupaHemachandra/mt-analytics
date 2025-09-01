package com.mt_analytics.mt_analytics.repository;

import com.mt_analytics.mt_analytics.entity.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralUserRepositoory extends JpaRepository<GeneralUser, Long> { }
