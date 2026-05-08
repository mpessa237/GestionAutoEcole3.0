package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.PaymentRequestDTO;
import com.felicite.SGAE30.dtos.PaymentResponseDTO;
import com.felicite.SGAE30.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
//@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/execute")
    public ResponseEntity<?> executePayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            return ResponseEntity.ok(paymentService.executePayment(paymentRequestDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/history/{registrationId}")
    public ResponseEntity<List<PaymentResponseDTO>> getHistory(@PathVariable Long registrationId) {
        return ResponseEntity.ok(paymentService.getPaymentHistory(registrationId));
    }

    @PatchMapping("/{paymentId}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("Payment cancelled successfully.");
    }

    @PatchMapping("/{paymentId}/active")
    public ResponseEntity<String> active(@PathVariable Long paymentId) {
        paymentService.activePayment(paymentId);
        return ResponseEntity.ok("Payment activated successfully.");
    }




}
