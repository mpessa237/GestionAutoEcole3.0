package com.felicite.SGAE30.mappers;

import com.felicite.SGAE30.dtos.VehicleRequestDTO;
import com.felicite.SGAE30.dtos.VehicleResponseDTO;
import com.felicite.SGAE30.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistration(vehicleRequestDTO.registration());
        vehicle.setModel(vehicleRequestDTO.model());
        vehicle.setMark(vehicleRequestDTO.mark());
        vehicle.setStatusVehicle(vehicleRequestDTO.statusVehicle());
        vehicle.setTypePermit(vehicleRequestDTO.typePermit());
        return vehicle;
    }

    public VehicleResponseDTO toDto(Vehicle vehicle) {
        return new VehicleResponseDTO(
                vehicle.getVehicleId(),
                vehicle.getRegistration(),
                vehicle.getModel(),
                vehicle.getMark(),
                vehicle.getStatusVehicle(),
                vehicle.getTypePermit()
        );
    }
}
