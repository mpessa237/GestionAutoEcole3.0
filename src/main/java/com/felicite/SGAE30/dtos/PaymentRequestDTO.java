package com.felicite.SGAE30.dtos;

import com.felicite.SGAE30.enums.PaymentMethod;

public record PaymentRequestDTO(
        Double amount,
        PaymentMethod paymentMethod,
        Long registrationId,
        String note
) {
}
