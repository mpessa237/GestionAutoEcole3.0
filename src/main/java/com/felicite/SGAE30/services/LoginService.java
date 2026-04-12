package com.felicite.SGAE30.services;

import com.felicite.SGAE30.configurations.JwtUtils;
import com.felicite.SGAE30.dtos.LoginRequest;
import com.felicite.SGAE30.dtos.LoginResponse;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager ;

    public LoginResponse authenticate(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        var user = userRepo.findByEmail(loginRequest.email())
                .orElseThrow(()-> new IllegalArgumentException("user not found!!"));

        var accessToken = jwtUtils.generateAccessToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);


        return new LoginResponse(accessToken,refreshToken);

    }
}
