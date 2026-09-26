package com.example.demo.repository;

import com.example.demo.model.MiniGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniGameRepository extends JpaRepository<MiniGame, Long> {
}
