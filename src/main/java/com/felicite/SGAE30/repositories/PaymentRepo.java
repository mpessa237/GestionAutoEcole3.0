package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.Payment;
import com.felicite.SGAE30.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.registration.registrationId = :regId")
    Double sumAmountByRegistration(@Param("regId") Long regId);

    List<Payment> findByRegistration(Registration registration);
}
