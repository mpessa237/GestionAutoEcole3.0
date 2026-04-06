package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.VehicleRequestDTO;
import com.felicite.SGAE30.dtos.VehicleResponseDTO;
import com.felicite.SGAE30.mappers.VehicleMapper;
import com.felicite.SGAE30.models.Vehicle;
import com.felicite.SGAE30.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepo vehicleRepo;
    private final VehicleMapper vehicleMapper;


    @Transactional
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        if (vehicleRepo.existsByRegistration(vehicleRequestDTO.registration())) {
            throw new RuntimeException("Véhicule déjà enregistré avec cette plaque.");
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        Vehicle savedVehicle = vehicleRepo.save(vehicle);

        return vehicleMapper.toDto(savedVehicle);
    }

    public List<VehicleResponseDTO> getAll() {
        return vehicleRepo.findAll().stream()
                .map(vehicleMapper::toDto)
                .toList();
    }
}
