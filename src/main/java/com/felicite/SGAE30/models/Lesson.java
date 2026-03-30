package com.felicite.SGAE30.models;

import com.felicite.SGAE30.enums.StatusLesson;
import com.felicite.SGAE30.enums.TypeLesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
    private LocalDate sessionDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private TypeLesson typeLesson;
    @Enumerated(EnumType.STRING)
    private StatusLesson statusLesson;

    @ManyToOne
    @JoinColumn(name = "monitor_id")
    private User monitor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
