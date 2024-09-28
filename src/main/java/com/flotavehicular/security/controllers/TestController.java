package com.flotavehicular.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/test")
public class TestController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<String> privateTest() {
        return ResponseEntity.ok("Only people with admin rank can use this endpoint");
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicTest() {
        return ResponseEntity.ok("Everyone can use this endpoint");
    }
}
