package com.felicite.SGAE30.dtos;

import com.felicite.SGAE30.enums.TypePermit;

public record RegistrationResponseDTO(
        Long registrationId,
        String fileNumber,
        String dateInscription,
        TypePermit typePermit,
        Double totalPrice,
        String firstnameStudent,
        String lastnameStudent,
        String firstnameAdmin
) {

}
