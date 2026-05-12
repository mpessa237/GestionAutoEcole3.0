package com.felicite.SGAE30.dtos;

public record PaymentRequestDTO(
        Double amount,
        String paymentMethod,
        Long registrationId,
        String note
) {
    public PaymentRequestDTO {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0");
        }
        if (registrationId == null) {
            throw new IllegalArgumentException("L'ID d'inscription est obligatoire");
        }
    }
}
