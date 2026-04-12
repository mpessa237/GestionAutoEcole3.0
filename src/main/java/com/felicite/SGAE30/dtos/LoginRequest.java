package com.felicite.SGAE30.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "email is mandatory")
        @Email(message = "Email not well formatted")
        String email,
        @NotBlank(message = "password is mandatory")
        String password
) {
}
