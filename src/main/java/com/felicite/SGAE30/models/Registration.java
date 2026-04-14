package com.felicite.SGAE30.models;

import com.felicite.SGAE30.enums.TypePermit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;
    private LocalDateTime registrationDate;
    private String fileNumber;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private TypePermit typePermit;

    @OneToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User createdBy;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL)
    private List<Payment> payments;
}
