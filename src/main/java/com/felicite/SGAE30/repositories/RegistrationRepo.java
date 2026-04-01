package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration,Long> {

    @Query("SELECT r FROM Registration r WHERE " +
            "(SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.registration = r AND p.paymentStatus = com.felicite.SGAE30.enums.PaymentStatus.VALID) < r.totalPrice")
    List<Registration> findIncompleteRegistrations();

}
