package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration,Long> {
}
