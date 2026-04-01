package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.enums.PaymentStatus;
import com.felicite.SGAE30.models.Payment;
import com.felicite.SGAE30.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {



    // N'oublie pas l'annotation @Query !
    // Sans elle, Spring cherche une propriété "sumAmountByRegistration" dans l'entité Payment
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.registration.registrationId = :regId AND p.paymentStatus = :status")
    Double sumValidAmountByRegistration(@Param("regId") Long regId, @Param("status") PaymentStatus status);

    // Si tu veux garder l'ancienne version simple pour tester :
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.registration.registrationId = :regId")
    Double sumAmountByRegistration(@Param("regId") Long regId);

    List<Payment> findByRegistration_RegistrationIdOrderByDatePaymentDesc(Long registrationId);
}
