package com.felicite.SGAE30.dtos;

public record RegistrationResponseDTO(
        Long id,
        String fileNumber,
        String dateInscription,
        com.felicite.SGAE30.enums.TypePermit typePermit,
        Double totalPrice,
        String firstnameStudent,
        String lastnameStudent,
        String firstnameAdmin
) {
}
