package com.felicite.SGAE30.dtos;

import com.felicite.SGAE30.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record UserRegistrationDTO(

        @NotBlank String firstname,
        @NotBlank String lastname,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String phoneNumber,
        @NotNull Role role // ADMIN, STUDENT, ou MONITOR
) {
}
