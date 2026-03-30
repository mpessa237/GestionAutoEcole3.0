package com.felicite.SGAE30.mappers;

import com.felicite.SGAE30.dtos.PaymentResponseDTO;
import com.felicite.SGAE30.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponseDTO toDto(Payment payment, Double remainingBalance) {
        return new PaymentResponseDTO(
                payment.getPaymentId(),
                payment.getReceiptNumber(),
                payment.getAmount(),
                payment.getDatePayment().toString(),
                payment.getPaymentMethod().name(),
                payment.getNote(),

                payment.getRegistration().getStudent().getFirstname() + " " +
                        payment.getRegistration().getStudent().getLastname(),

                payment.getRegistration().getFileNumber(),

                payment.getCreatedBy().getFirstname(),

                remainingBalance
        );
    }
}
