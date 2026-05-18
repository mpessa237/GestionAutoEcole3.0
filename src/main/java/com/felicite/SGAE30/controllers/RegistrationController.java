package com.felicite.SGAE30.controllers;

import com.felicite.SGAE30.dtos.*;
import com.felicite.SGAE30.enums.DebtorResponseDTO;
import com.felicite.SGAE30.mappers.RegistrationMapper;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.services.LoginService;
import com.felicite.SGAE30.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/registrations")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        String message = registrationService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/create")
    public ResponseEntity<RegistrationResponseDTO> create(@RequestBody RegistrationRequestDTO requestDTO){
        RegistrationResponseDTO registration = registrationService.registerStudent(requestDTO);
        return ResponseEntity.ok(registration);
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

    @PatchMapping("/{userId}/update")
    public ResponseEntity<StudentResponseDTO> patchUpdate(@PathVariable Long userId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(registrationService.patchStudent(userId, updates));
    }

    @GetMapping("/{students}/{userId}/{details}")
    public ResponseEntity<StudentDetailsResponseDTO> getStudentDetails(@PathVariable Long userId){
        return ResponseEntity.ok(registrationService.getStudentCompleteDetails(userId));
    }


}
