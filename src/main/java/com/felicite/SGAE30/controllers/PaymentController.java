package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.PaymentRequestDTO;
import com.felicite.SGAE30.dtos.PaymentResponseDTO;
import com.felicite.SGAE30.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/execute")
    public ResponseEntity<PaymentResponseDTO> execute(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = paymentService.executePayment(paymentRequestDTO);
        return ResponseEntity.ok(paymentResponseDTO);
    }
}
