package com.felicite.SGAE30.dtos;

import com.felicite.SGAE30.enums.StatusVehicle;
import com.felicite.SGAE30.enums.TypePermit;

public record VehicleResponseDTO(
        Long VehicleId,
        String registration,
        String model,
        String mark,
        StatusVehicle statusVehicle,
        TypePermit typePermit
) {
}
