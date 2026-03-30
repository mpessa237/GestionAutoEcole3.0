package com.felicite.SGAE30.mappers;

import com.felicite.SGAE30.dtos.RegistrationResponseDTO;
import com.felicite.SGAE30.models.Registration;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public RegistrationResponseDTO toDto(Registration registration) {
        return new RegistrationResponseDTO(
                registration.getRegistrationId(),
                registration.getFileNumber(),
                registration.getRegistrationDate().toString(),
                registration.getTypePermit(),
                registration.getTotalPrice(),
                registration.getStudent().getLastname(),
                registration.getStudent().getFirstname(),
                registration.getCreatedBy().getLastname()
        );
    }
}
