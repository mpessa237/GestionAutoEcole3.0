package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.LoginRequest;
import com.felicite.SGAE30.dtos.LoginResponse;
import com.felicite.SGAE30.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(loginService.authenticate(loginRequest));
    }
}
