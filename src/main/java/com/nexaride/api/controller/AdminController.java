package com.nexaride.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public String adminOnly() {
        return "Admin access granted";
    }
}