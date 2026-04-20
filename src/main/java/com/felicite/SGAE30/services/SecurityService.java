package com.felicite.SGAE30.services;

import com.felicite.SGAE30.repositories.RegistrationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {

    private final RegistrationRepo registrationRepo;

    //compare email du token  avec email stocker en bd ,pour le student ne vois que ses propre paiement
    public boolean isOwnerOfRegistration(Long registrationId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return registrationRepo.findById(registrationId)
                .map(reg -> reg.getStudent().getEmail().equals(currentUserEmail))
                .orElse(false);
    }
}
