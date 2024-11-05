package com.flotavehicular.security.controllers;

import com.flotavehicular.security.dto.UserDto;
import com.flotavehicular.security.services.impl.UserServiceImpl;
import com.flotavehicular.security.utils.PageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @Value("${page.size}")
    private int defaultPageSize;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        int effectivePageSize = (pageSize != null) ? pageSize : defaultPageSize;

        PageResponse<UserDto> carPageResponse = userService.getAllUsers(pageNumber, effectivePageSize);

        return ResponseEntity.ok(carPageResponse);
    }

}
