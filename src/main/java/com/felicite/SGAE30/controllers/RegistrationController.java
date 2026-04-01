package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.RegistrationRequestDTO;
import com.felicite.SGAE30.dtos.RegistrationResponseDTO;
import com.felicite.SGAE30.dtos.StudentResponseDTO;
import com.felicite.SGAE30.enums.DebtorResponseDTO;
import com.felicite.SGAE30.mappers.RegistrationMapper;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    @PostMapping("/create")
    public ResponseEntity<RegistrationResponseDTO> create(@RequestBody RegistrationRequestDTO requestDTO){
        Registration registration = registrationService.registerStudent(requestDTO);
        RegistrationResponseDTO registrationResponseDTO = registrationMapper.toDto(registration);

        return ResponseEntity.ok(registrationResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return ResponseEntity.ok(registrationService.getAllStudents());
    }

    @GetMapping("/active")
    public ResponseEntity<List<StudentResponseDTO>> getActiveStudents() {
        return ResponseEntity.ok(registrationService.getAllActiveStudents());
    }

    @GetMapping("/archived")
    public ResponseEntity<List<StudentResponseDTO>> getArchivedStudents() {
        return ResponseEntity.ok(registrationService.getAllArchivedStudents());
    }

    @PatchMapping("/{userId}/disable")
    public ResponseEntity<String> disableStudent(@PathVariable Long userId) {
        registrationService.disableStudent(userId);
        return ResponseEntity.ok(" the account student is disabled successfully.");
    }

    @PatchMapping("/{userId}/enable")
    public ResponseEntity<String> enableStudent(@PathVariable Long userId) {
        registrationService.enableStudent(userId);
        return ResponseEntity.ok("the account student reactive successfully.");
    }


}
