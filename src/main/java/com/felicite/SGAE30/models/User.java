package com.felicite.SGAE30.models;

import com.felicite.SGAE30.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String phoneNumber;

    private boolean enabled = true;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "createdBy")
    private List<Registration> registrations;

    @OneToMany(mappedBy = "monitor")
    private List<Lesson> lessons;

    @OneToOne(mappedBy = "student")
    private Registration fileRegistration;
}
