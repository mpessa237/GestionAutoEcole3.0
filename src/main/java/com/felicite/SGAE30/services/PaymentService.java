package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.PaymentRequestDTO;
import com.felicite.SGAE30.dtos.PaymentResponseDTO;
import com.felicite.SGAE30.enums.PaymentStatus;
import com.felicite.SGAE30.mappers.PaymentMapper;
import com.felicite.SGAE30.models.Payment;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.PaymentRepo;
import com.felicite.SGAE30.repositories.RegistrationRepo;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
    @RequiredArgsConstructor
    public class PaymentService {

        private final PaymentRepo paymentRepo;
        private final RegistrationRepo registrationRepo;
        private final UserRepo userRepo;
        private final PaymentMapper paymentMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponseDTO executePayment(PaymentRequestDTO paymentRequestDTO) {

        Registration reg = registrationRepo.findById(paymentRequestDTO.registrationId())
                .orElseThrow(() -> new RuntimeException("registration not found with ID : " + paymentRequestDTO.registrationId()));

        Double currentTotalPaid = paymentRepo.sumAmountByRegistration(reg.getRegistrationId());
        if (currentTotalPaid == null) currentTotalPaid = 0.0;
        Double remainingBefore = reg.getTotalPrice() - currentTotalPaid;

        if (paymentRequestDTO.amount() > remainingBefore) {
            throw new RuntimeException("Transaction cancelled : The amount exceeds the remaining balance due (" + remainingBefore + " CFA)");
        }

        String currentAdminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userRepo.findByEmail(currentAdminEmail)
                .orElseThrow(() -> new RuntimeException("Admin connected not found en base "));

        Payment payment = new Payment();
        payment.setAmount(paymentRequestDTO.amount());
        payment.setPaymentMethod(paymentRequestDTO.paymentMethod());
        payment.setRegistration(reg);
        payment.setCreatedBy(admin);
        payment.setNote(paymentRequestDTO.note());
        payment.setDatePayment(LocalDateTime.now());

        payment.setReceiptNumber("REC-" + LocalDate.now().getYear() + "-" + (System.currentTimeMillis() % 1000000));

        Payment savedPayment = paymentRepo.save(payment);

        Double newRemaining = remainingBefore - paymentRequestDTO.amount();

        return paymentMapper.toDto(savedPayment, newRemaining);
    }



    public List<PaymentResponseDTO> getPaymentHistory(Long registrationId) {
        Registration reg = registrationRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException(" file note found"));

        List<Payment> allPayments = paymentRepo.findByRegistration_RegistrationIdOrderByDatePaymentDesc(registrationId);

        Double totalValidPaid = allPayments.stream()
                .filter(p -> p.getPaymentStatus() == PaymentStatus.VALID)
                .mapToDouble(Payment::getAmount)
                .sum();

        Double trueRemaining = reg.getTotalPrice() - totalValidPaid;

        return allPayments.stream()
                .map(p -> paymentMapper.toDto(p, trueRemaining))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelPayment(Long paymentId){
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(()-> new RuntimeException("payment not found!!!"));
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        payment.setNote(payment.getNote() + " (cancel the " + LocalDateTime.now() + ")");

        paymentRepo.save(payment);
    }

    @Transactional
    public void activePayment(Long paymentId){

        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(()-> new RuntimeException("payment not found!!"));

        payment.setPaymentStatus(PaymentStatus.VALID);
        payment.setNote(payment.getNote() + "(active the " + LocalDateTime.now() +")");

        paymentRepo.save(payment);
    }




}
