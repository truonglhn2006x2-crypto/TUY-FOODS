package com.example.demo.repository;

import com.example.demo.model.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
    List<GameResult> findByUserId(Long userId);
}
