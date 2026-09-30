package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(
            @PathVariable Long userId
    ) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
    @PostMapping("/{userId}/minigame-key")
    public ResponseEntity<?> addMiniGameKey(
            @PathVariable Long userId
    ) {
        return userRepository.findById(userId)
                .map(user -> {

                    Integer keys = user.getMiniGameKeys();

                    if (keys == null) {
                        keys = 0;
                    }

                    user.setMiniGameKeys(keys + 1);

                    userRepository.save(user);

                    return ResponseEntity.ok(user);
                })
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }
}