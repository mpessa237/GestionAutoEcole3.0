package com.felicite.SGAE30.dtos;

public record PaymentResponseDTO(
        Long paymentId,
        String receiptNumber,
        Double amount,
        String datePayment,
        String paymentMethod,
        String note,

        String studentFullName,
        String fileNumber,

        String adminName,

        Double remainingBalance
) {
}
