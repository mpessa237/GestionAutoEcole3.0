package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.RegistrationRequestDTO;
import com.felicite.SGAE30.dtos.StudentResponseDTO;
import com.felicite.SGAE30.dtos.UserRegistrationDTO;
import com.felicite.SGAE30.enums.Role;
import com.felicite.SGAE30.enums.TypePermit;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.RegistrationRepo;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepo userRepo;
    private final RegistrationRepo registrationRepo;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public String registerUser(UserRegistrationDTO registrationDTO){

        if (userRepo.findByEmail(registrationDTO.email()).isPresent()){
            throw new RuntimeException("Error:Email already exists!!");
        }

        User user = new User();
        user.setFirstname(registrationDTO.firstname());
        user.setLastname(registrationDTO.lastname());
        user.setEmail(registrationDTO.email());
        user.setPhoneNumber(registrationDTO.phoneNumber());
        user.setRole(registrationDTO.role());

        user.setPassword(passwordEncoder.encode(registrationDTO.password()));

        userRepo.save(user);

        return "user save" +registrationDTO.role();
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Registration registerStudent(RegistrationRequestDTO requestDTO) {

        if (userRepo.findByEmail(requestDTO.email()).isPresent()){
            throw new RuntimeException(" email already exist!!");
        }

        User student = new User();
        student.setFirstname(requestDTO.firstname());
        student.setLastname(requestDTO.lastname());
        student.setEmail(requestDTO.email());
        student.setPhoneNumber(requestDTO.phoneNumber());
        student.setRole(Role.STUDENT);
        student.setPassword(passwordEncoder.encode(requestDTO.password()));
        User savedStudent = userRepo.save(student);

        Registration registration = new Registration();
        registration.setStudent(savedStudent);

        String adminEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User admin = userRepo.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
        registration.setCreatedBy(admin);

        TypePermit type = TypePermit.valueOf(requestDTO.typePermit());
        registration.setTypePermit(type);

        Double finalPrice = (requestDTO.totalPrice() != null) ? requestDTO.totalPrice() : type.getDefaultPrice();
        registration.setTotalPrice(finalPrice);

        registration.setRegistrationDate(LocalDateTime.now());

        registration.setFileNumber("AE-" + LocalDate.now().getYear() + "-" + System.currentTimeMillis() % 100000);

        return registrationRepo.save(registration);
    }


    public List<StudentResponseDTO> getAllStudents() {
        return userRepo.findByRole(Role.STUDENT).stream()
                .map(user -> new StudentResponseDTO(
                        user.getUserId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getPhoneNumber(),
                        user.getFileRegistration() != null ? user.getFileRegistration().getFileNumber() : "N/A",
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void disableStudent(Long userId) {
        User student = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("student not found"));

        student.setEnabled(false);
        userRepo.save(student);
    }



    @Transactional
    public void enableStudent(Long userId) {
        User student = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("student not found"));

        student.setEnabled(true);
        userRepo.save(student);
    }

    public List<StudentResponseDTO> getAllActiveStudents() {
        return userRepo.findByRoleAndEnabledTrue(Role.STUDENT).stream()
                .map(user -> new StudentResponseDTO(
                        user.getUserId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getPhoneNumber(),
                        user.getFileRegistration() != null ? user.getFileRegistration().getFileNumber() : "N/A",
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

   public List<StudentResponseDTO> getAllArchivedStudents(){
        return userRepo.findByRoleAndEnabledFalse(Role.STUDENT).stream()
                .map(user -> new StudentResponseDTO(
                        user.getUserId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getPhoneNumber(),
                        user.getFileRegistration() != null ? user.getFileRegistration().getFileNumber() : "N/A",
                        user.getEmail()
                ))
                .collect(Collectors.toList());
   }


}
