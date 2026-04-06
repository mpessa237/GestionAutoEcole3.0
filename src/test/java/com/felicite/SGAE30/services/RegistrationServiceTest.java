package com.felicite.SGAE30.services;

import com.felicite.SGAE30.dtos.RegistrationRequestDTO;
import com.felicite.SGAE30.models.Registration;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.RegistrationRepo;
import com.felicite.SGAE30.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RegistrationRepo registrationRepo;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void registerStudent() {

        //arrange
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(
                "Herve","dev","657938010","hervempessa@gmail.com","password",250000.0,"B",1L
        );

        User admin = new User();
        admin.setUserId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(admin));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        when(registrationRepo.save(any(Registration.class))).thenAnswer(i -> i.getArgument(0));

        //act

        Registration result = registrationService.registerStudent(requestDTO);

        //assert
        assertNotNull(result);
        assertEquals("Herve", result.getStudent().getFirstname());
        assertTrue(result.getFileNumber().startsWith("AE-2026-"));

        // On vérifie que les repos ont été sollicités
        verify(userRepo).findById(1L);
        verify(registrationRepo).save(any(Registration.class));

    }

    @Test
    void shouldDisableStudentSuccessfully(){
        // arrange

        Long studentId = 10L;
        User existingStudent = new User();
        existingStudent.setUserId(studentId);
        existingStudent.setEnabled(true);

        when(userRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(userRepo.save(any(User.class))).thenAnswer(i-> i.getArgument(0));

        //act
        registrationService.disableStudent(studentId);

        //assert
        assertFalse(existingStudent.isEnabled(),"student disable(enabled=false)");
        verify(userRepo,times(1)).findById(studentId);
        verify(userRepo,times(1)).save(existingStudent);

    }

    @Test
    void shouldEnableStudentSuccessfully(){
        //arrange
        Long userId = 10L;
        User user = new User();
        user.setUserId(userId);
        user.setEnabled(false);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //act
        registrationService.enableStudent(userId);

        //assert
        assertTrue(user.isEnabled(),"student enable=true");
        verify(userRepo,times(1)).findById(userId);
        verify(userRepo,times(1)).save(any(User.class));
    }
}