package com.example.demo.controller;

import com.example.demo.model.GameResult;
import com.example.demo.service.MiniGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/minigame")
public class MiniGameController {

    private final MiniGameService miniGameService;

    public MiniGameController(MiniGameService miniGameService) {
        this.miniGameService = miniGameService;
    }

    @PostMapping("/play")
    public ResponseEntity<?> playGame(
            @RequestParam Long userId,
            @RequestParam Long gameId
    ) {
        try {
            GameResult result =
                    miniGameService.playGame(userId, gameId);

            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/exchange")
    public ResponseEntity<?> exchangePointsForKeys(
            @RequestParam Long userId,
            @RequestParam int keys
    ) {
        try {

            User user =
                    miniGameService.exchangePointsForKeys(
                            userId,
                            keys
                    );

            return ResponseEntity.ok(
                    java.util.Map.of(
                            "message", "Đổi điểm thành công",
                            "points", user.getPoints(),
                            "miniGameKeys", user.getMiniGameKeys()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/results/{userId}")
    public ResponseEntity<?> getUserGameResults(
            @PathVariable Long userId
    ) {
        try {
            List<GameResult> results =
                    miniGameService.getUserGameResults(userId);

            return ResponseEntity.ok(results);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}