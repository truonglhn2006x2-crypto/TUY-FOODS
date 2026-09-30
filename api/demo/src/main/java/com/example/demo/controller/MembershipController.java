package com.example.demo.controller;

import com.example.demo.model.Membership;
import com.example.demo.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping("/{userId}")
    public Membership getMembership(
            @PathVariable Long userId
    ) {
        return membershipService.getMembership(userId);
    }
}