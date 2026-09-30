package com.example.demo.service;

import com.example.demo.model.GameResult;
import com.example.demo.model.MiniGame;
import com.example.demo.model.User;
import com.example.demo.repository.GameResultRepository;
import com.example.demo.repository.MiniGameRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class MiniGameService {

    private final UserRepository userRepository;
    private final MiniGameRepository miniGameRepository;
    private final GameResultRepository gameResultRepository;

    private final Random random = new Random();

    public MiniGameService(
            UserRepository userRepository,
            MiniGameRepository miniGameRepository,
            GameResultRepository gameResultRepository
    ) {
        this.userRepository = userRepository;
        this.miniGameRepository = miniGameRepository;
        this.gameResultRepository = gameResultRepository;
    }

    @Transactional
    public GameResult playGame(Long userId, Long gameId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy người dùng")
                );

        MiniGame game = miniGameRepository.findById(gameId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy mini game")
                );

        if (!Boolean.TRUE.equals(game.getStatus())) {
            throw new RuntimeException("Mini game hiện đang tắt");
        }

        Integer keys = user.getMiniGameKeys();

        if (keys == null || keys <= 0) {
            throw new RuntimeException("Bạn đã hết chìa khóa");
        }

        // Trừ 1 chìa khóa
        user.setMiniGameKeys(keys - 1);

        // Random từ 0 -> 99
        int randomNumber = random.nextInt(100);

        String rewardCode;
        int reward;

        if (randomNumber < 35) {

            // 35%
            // 100 điểm
            rewardCode = "points_100";
            reward = 100;

            Integer currentPoints = user.getPoints();

            if (currentPoints == null) {
                currentPoints = 0;
            }

            user.setPoints(currentPoints + 100);

        } else if (randomNumber < 60) {

            // 25%
            // Không nhận gì
            rewardCode = "nothing";
            reward = 0;

        } else if (randomNumber < 80) {

            // 20%
            // Giảm 10%
            rewardCode = "discount_10";
            reward = 10;

        } else if (randomNumber < 92) {

            // 12%
            // Giảm 20.000đ
            rewardCode = "discount_20k";
            reward = 20000;

        } else if (randomNumber < 99) {

            // 7%
            // Miễn phí vận chuyển
            rewardCode = "free_ship";
            reward = 0;

        } else {

            // 1%
            // Một món miễn phí
            rewardCode = "free_food";
            reward = 1;
        }

        userRepository.save(user);

        // Lưu kết quả chơi
        GameResult result = new GameResult();

        result.setUser(user);
        result.setGame(game);
        result.setScore(0);
        result.setReward(reward);
        result.setRewardCode(rewardCode);

        return gameResultRepository.save(result);
    }

    @Transactional
    public User exchangePointsForKeys(
            Long userId,
            int keys
    ) {

        if (keys <= 0) {
            throw new RuntimeException(
                    "Số chìa khóa không hợp lệ"
            );
        }

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy người dùng"
                                )
                        );

        int cost = keys * 100;

        int currentPoints =
                user.getPoints() == null
                        ? 0
                        : user.getPoints();

        if (currentPoints < cost) {
            throw new RuntimeException(
                    "Không đủ điểm để đổi chìa khóa"
            );
        }

        int currentKeys =
                user.getMiniGameKeys() == null
                        ? 0
                        : user.getMiniGameKeys();

        user.setPoints(
                currentPoints - cost
        );

        user.setMiniGameKeys(
                currentKeys + keys
        );

        return userRepository.save(user);
    }

    public List<GameResult> getUserGameResults(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }

        return gameResultRepository.findByUserId(userId);
    }
}