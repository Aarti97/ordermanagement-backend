package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.LoginRequest;
import com.example.ordermanagement.dto.LoginResponse;
import com.example.ordermanagement.dto.RegisterRequest;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.security.CustomUserDetails;
import com.example.ordermanagement.service.UserService;
import com.example.ordermanagement.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
   

    public AuthController(UserService userService,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        User user = userService.register(
                req.getUsername(),
                req.getPassword(),
                req.getRole()
        );

        return ResponseEntity.ok("User registered with role " + user.getRole());
    }

    // ✅ LOGIN
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
//
//        // 🔐 authenticate user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        req.getUsername(),
//                        req.getPassword()
//                )
//        );
//
//        // 👤 get user details
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        // 🎭 get role from DB
//        String role = userDetails.getAuthorities()
//                .iterator()
//                .next()
//                .getAuthority();
//
//        // 🔑 generate JWT with role
//        String token = jwtUtil.generateToken(userDetails.getUsername(), role);
//
//        return ResponseEntity.ok(
//                new LoginResponse(
//                        token,
//                        userDetails.getUsername(),
//                        role
//                )
//        );
//    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );

            // ✅ Get CustomUserDetails directly
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            Long userId = userDetails.getId();   // 👈 correct type

            String token = jwtUtil.generateToken(
                    userDetails.getUsername(),
                    role
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            userDetails.getUsername(),
                            role,
                            userId
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body("Invalid username or password");
        }
    }
}