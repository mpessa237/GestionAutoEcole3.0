package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.VehicleRequestDTO;
import com.felicite.SGAE30.dtos.VehicleResponseDTO;
import com.felicite.SGAE30.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@RequestBody VehicleRequestDTO vehicleRequestDTO){
        return ResponseEntity.ok(vehicleService.createVehicle(vehicleRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAll(){
        return ResponseEntity.ok(vehicleService.getAll());
    }
}
