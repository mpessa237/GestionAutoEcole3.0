package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.VehicleRequestDTO;
import com.felicite.SGAE30.dtos.VehicleResponseDTO;
import com.felicite.SGAE30.enums.StatusVehicle;
import com.felicite.SGAE30.mappers.VehicleMapper;
import com.felicite.SGAE30.models.Vehicle;
import com.felicite.SGAE30.repositories.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepo vehicleRepo;
    private final VehicleMapper vehicleMapper;


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public VehicleResponseDTO createVehicle(VehicleRequestDTO dto) {
        if (vehicleRepo.existsByRegistration(dto.registration())) {
            throw new RuntimeException("Véhicule déjà enregistré avec cette plaque.");
        }

        Vehicle vehicle = vehicleMapper.toEntity(dto);

        vehicle.setStatusVehicle(StatusVehicle.AVAILABLE);

        Vehicle savedVehicle = vehicleRepo.save(vehicle);
        return vehicleMapper.toDto(savedVehicle);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<VehicleResponseDTO> getAll() {
        return vehicleRepo.findAll().stream()
                .map(vehicleMapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO vehicleRequestDTO,Long vehicleId){
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(()-> new RuntimeException("vehicle not found!!"));

        vehicle.setMark(vehicleRequestDTO.mark());
        vehicle.setModel(vehicleRequestDTO.model());
        vehicle.setRegistration(vehicleRequestDTO.registration());
        
        return vehicleMapper.toDto(vehicleRepo.save(vehicle));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void updateVehicleStatus(StatusVehicle statusVehicle,Long vehicleId){
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(()-> new RuntimeException("vehicle not found!!"));
        vehicle.setStatusVehicle(statusVehicle);
        vehicleRepo.save(vehicle);
    }
}
