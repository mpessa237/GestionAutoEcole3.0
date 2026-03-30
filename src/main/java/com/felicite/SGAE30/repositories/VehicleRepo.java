package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,Long> {
}
