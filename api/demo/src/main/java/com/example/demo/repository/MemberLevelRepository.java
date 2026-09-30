package com.example.demo.repository;

import com.example.demo.model.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberLevelRepository extends JpaRepository<MemberLevel, Long> {

    Optional<MemberLevel> findTopByOrderByMinSpendingDesc();
}
