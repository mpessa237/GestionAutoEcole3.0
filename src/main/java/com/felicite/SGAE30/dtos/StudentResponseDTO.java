package com.felicite.SGAE30.dtos;

import lombok.*;

@NoArgsConstructor
@Data
@Getter
@Setter
public class StudentResponseDTO{
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String fileNumber;
    private String email;
    boolean enabled;
    Long registrationId;

    public StudentResponseDTO(Long userId, String firstname, String lastname, String phoneNumber,
                              String fileNumber, String email, boolean enabled, Long registrationId) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.fileNumber = fileNumber;
        this.email = email;
        this.enabled = enabled;
        this.registrationId = registrationId;
    }


}
