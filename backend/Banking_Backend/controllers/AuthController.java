package com.banking.backend.Banking_Backend.controllers;

import com.banking.backend.Banking_Backend.dto.UserDTO;
import com.banking.backend.Banking_Backend.entites.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = (User) authentication.getPrincipal();
        UserDTO userDTO = new UserDTO(user.getUserId (), user.getName(), user.getEmail(), user.getPhoneNumber ( ));

        return ResponseEntity.ok(userDTO);
    }
}
