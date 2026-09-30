package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        return userRepository.save(user);
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Sai mật khẩu");
        }

        // Chỉ CUSTOMER mới được nhận điểm đăng nhập hằng ngày
        if (user.getRole() == User.Role.CUSTOMER) {

            java.time.LocalDate today =
                    java.time.LocalDate.now();

            // Hôm nay chưa nhận điểm
            if (!today.equals(user.getLastDailyLogin())) {

                int currentPoints =
                        user.getPoints() == null
                                ? 0
                                : user.getPoints();

                user.setPoints(currentPoints + 100);

                user.setLastDailyLogin(today);

                userRepository.save(user);
            }
        }

        return user;
    }
}