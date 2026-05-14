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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private RegistrationRepo registrationRepo;
    @Mock
    private PaymentRepo paymentRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void executePayment_ShouldSuccess_WhenAmountIsValide() {
        Long registrationId = 1L;
        PaymentRequestDTO request = new PaymentRequestDTO(50000.0, "CASH", registrationId, "Avance");

        Registration reg = new Registration();
        reg.setRegistrationId(registrationId);
        reg.setTotalPrice(150000.0);

        User admin = new User();
        admin.setEmail("hervempessa7@gmail.com");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("hervempessa7@gmail.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth); // Mock direct

        SecurityContextHolder.setContext(securityContext);

        when(registrationRepo.findById(registrationId)).thenReturn(Optional.of(reg));
        when(paymentRepo.sumAmountByRegistration(registrationId)).thenReturn(50000.0);
        when(userRepo.findByEmail("hervempessa7@gmail.com")).thenReturn(Optional.of(admin));

        when(paymentRepo.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        when(paymentMapper.toDto(any(Payment.class), anyDouble()))
                .thenReturn(new PaymentResponseDTO(
                        1L, "REC-2026-123456", 50000.0,
                        LocalDateTime.now().toString(), "CASH", "Avance",
                        "Herve Mpessa", "FILE-001", "Admin", 50000.0
                ));

        PaymentResponseDTO result = paymentService.executePayment(request);

        assertNotNull(result);
        assertEquals(50000.0, result.remainingBalance());
        verify(paymentRepo, times(1)).save(any(Payment.class));
    }


}