package com.example.demo.service;

import com.example.demo.model.MemberLevel;
import com.example.demo.model.Membership;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.MemberLevelRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MembershipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    public Membership getMembership(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        BigDecimal totalSpent =
                orderRepository.getTotalCompletedAmount(
                        userId,
                        Order.Status.COMPLETED
                );

        if (totalSpent == null) {
            totalSpent = BigDecimal.ZERO;
        }

        List<MemberLevel> levels =
                memberLevelRepository.findAll();

        // Sắp xếp từ mức chi tiêu thấp → cao
        levels.sort(
                (a, b) -> a.getMinSpending()
                        .compareTo(b.getMinSpending())
        );

        MemberLevel currentLevel = null;
        MemberLevel nextLevel = null;

        // Xác định hạng hiện tại và hạng tiếp theo
        for (int i = 0; i < levels.size(); i++) {

            MemberLevel level = levels.get(i);

            if (totalSpent.compareTo(level.getMinSpending()) >= 0) {

                currentLevel = level;

                if (i + 1 < levels.size()) {
                    nextLevel = levels.get(i + 1);
                }
            }
        }

        // Nếu không tìm thấy thì mặc định là mức thấp nhất
        if (currentLevel == null && !levels.isEmpty()) {
            currentLevel = levels.get(0);

            if (levels.size() > 1) {
                nextLevel = levels.get(1);
            }
        }

        String levelName =
                currentLevel != null
                        ? currentLevel.getName()
                        : "Member";

        String nextLevelName = null;
        BigDecimal remainingAmount = BigDecimal.ZERO;
        int progressPercent = 100;

        if (nextLevel != null) {

            nextLevelName = nextLevel.getName();

            remainingAmount =
                    nextLevel.getMinSpending()
                            .subtract(totalSpent);

            if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                remainingAmount = BigDecimal.ZERO;
            }

            BigDecimal currentMin =
                    currentLevel.getMinSpending();

            BigDecimal nextMin =
                    nextLevel.getMinSpending();

            BigDecimal range =
                    nextMin.subtract(currentMin);

            if (range.compareTo(BigDecimal.ZERO) > 0) {

                BigDecimal progress =
                        totalSpent
                                .subtract(currentMin)
                                .multiply(BigDecimal.valueOf(100))
                                .divide(
                                        range,
                                        0,
                                        java.math.RoundingMode.DOWN
                                );

                progressPercent = progress.intValue();

                if (progressPercent < 0) {
                    progressPercent = 0;
                }

                if (progressPercent > 100) {
                    progressPercent = 100;
                }
            }

        } else {
            // Đã đạt hạng cao nhất
            progressPercent = 100;
        }

        List<String> unlockedFeatures = new java.util.ArrayList<>();
        List<String> lockedFeatures = new java.util.ArrayList<>();

        if (currentLevel != null) {

            for (MemberLevel level : levels) {

                String benefit = level.getBenefits();

                if (benefit == null || benefit.trim().isEmpty()) {
                    continue;
                }

                if (level.getMinSpending()
                        .compareTo(currentLevel.getMinSpending()) <= 0) {

                    unlockedFeatures.add(benefit);

                } else {

                    lockedFeatures.add(benefit);
                }
            }
        }

        return new Membership(
                user.getId(),
                user.getName(),
                levelName,
                totalSpent,
                nextLevelName,
                remainingAmount,
                progressPercent,
                unlockedFeatures,
                lockedFeatures
        );
    }
}