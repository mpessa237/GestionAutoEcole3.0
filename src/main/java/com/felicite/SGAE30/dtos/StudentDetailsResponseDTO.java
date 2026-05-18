package com.felicite.SGAE30.dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsResponseDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String fileNumber;
    private Long registrationId;
    private List<PaymentResponseDTO> payments;




}
