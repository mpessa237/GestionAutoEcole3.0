package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.RegistrationRequestDTO;
import com.felicite.SGAE30.dtos.StudentResponseDTO;
import com.felicite.SGAE30.enums.Role;
import com.felicite.SGAE30.enums.TypePermit;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.RegistrationRepo;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepo userRepo;
    private final RegistrationRepo registrationRepo;


    @Transactional
    public Registration registerStudent(RegistrationRequestDTO requestDTO) {

        User admin = userRepo.findById(requestDTO.adminId())
                .orElseThrow(() -> new RuntimeException("admin not found!!"));

        User student = new User();
        student.setFirstname(requestDTO.firstname());
        student.setLastname(requestDTO.lastname());
        student.setEmail(requestDTO.email());
        student.setPhoneNumber(requestDTO.phoneNumber());
        student.setRole(Role.STUDENT);
        student.setPassword("123456789");

        User savedStudent = userRepo.save(student);

        Registration registration = new Registration();
        registration.setStudent(savedStudent);
        registration.setCreatedBy(admin);

        registration.setTypePermit(TypePermit.valueOf(requestDTO.typePermit()));

        TypePermit type = TypePermit.valueOf(requestDTO.typePermit());
        registration.setTotalPrice(type.getDefaultPrice());

        registration.setTotalPrice(requestDTO.totalPrice());
        registration.setRegistrationDate(LocalDateTime.now());

        registration.setFileNumber("AE-" + LocalDate.now().getYear() + "-" + System.currentTimeMillis());

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
