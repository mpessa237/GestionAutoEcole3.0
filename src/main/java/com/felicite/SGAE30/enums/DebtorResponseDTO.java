package com.felicite.SGAE30.enums;

public record DebtorResponseDTO(
        Long registrationId,
        String studentFullName,
        String phoneNumber,
        String typePermit,
        Double totalPrice,
        Double totalPaid,
        Double remainingAmount,
        String lastPaymentDate
) {
}
