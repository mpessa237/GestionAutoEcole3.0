package com.felicite.SGAE30.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponseDTO{
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String fileNumber;
    private String email;
    boolean enabled;
    Long registrationId;


    public StudentResponseDTO(Long userId, String firstname, String lastname, String phoneNumber, String s, String email, boolean enabled) {
    }
}
