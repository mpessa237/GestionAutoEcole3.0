package com.felicite.SGAE30.models;

import com.felicite.SGAE30.enums.StatusVehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;
    @Column(nullable = false,unique = true)
    private String registration;
    private String model;
    private String mark;
    @Enumerated(EnumType.STRING)
    private StatusVehicle statusVehicle;
    private String typePermit;

    @OneToMany(mappedBy = "vehicle")
    private List<Lesson> lessonList ;
}
