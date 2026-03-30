package com.felicite.SGAE30.dtos;

public record RegistrationRequestDTO(
        String firstname,
        String lastname,
        String phoneNumber,
        String email,
        String password,

        Double totalPrice,
        String typePermit,
        Long adminId
) {
}
