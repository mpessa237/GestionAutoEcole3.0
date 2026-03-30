package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.PaymentRequestDTO;
import com.felicite.SGAE30.dtos.PaymentResponseDTO;
import com.felicite.SGAE30.mappers.PaymentMapper;
import com.felicite.SGAE30.models.Payment;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.PaymentRepo;
import com.felicite.SGAE30.repositories.RegistrationRepo;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
    @RequiredArgsConstructor
    public class PaymentService {

        private final PaymentRepo paymentRepo;
        private final RegistrationRepo registrationRepo;
        private final UserRepo userRepo;
        private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDTO executePayment(PaymentRequestDTO paymentRequestDTO) {

        Registration reg = registrationRepo.findById(paymentRequestDTO.registrationId())
                .orElseThrow(() -> new RuntimeException("Registration not found ID : " + paymentRequestDTO.registrationId()));

        Double currentTotalPaid = paymentRepo.sumAmountByRegistration(reg.getRegistrationId());
        if (currentTotalPaid == null) currentTotalPaid = 0.0;

        Double remainingBefore = reg.getTotalPrice() - currentTotalPaid;

        if (paymentRequestDTO.amount() > remainingBefore) {
            throw new RuntimeException("Transaction declined: The amount (" + paymentRequestDTO.amount() +
                    "FCFA) is greater than the remaining amount to be paid (" + remainingBefore + " FCFA)");
        }

        User admin = userRepo.findById(paymentRequestDTO.adminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Payment payment = new Payment();
        payment.setAmount(paymentRequestDTO.amount());
        payment.setPaymentMethod(paymentRequestDTO.paymentMethod());
        payment.setRegistration(reg);
        payment.setCreatedBy(admin);
        payment.setNote(paymentRequestDTO.note());
        payment.setDatePayment(LocalDateTime.now());

        payment.setReceiptNumber("REC-" + LocalDate.now().getYear() + "-" + System.currentTimeMillis());

        Payment savedPayment = paymentRepo.save(payment);

        Double newRemaining = remainingBefore - paymentRequestDTO.amount();

        return paymentMapper.toDto(savedPayment, newRemaining);
    }
}
