package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepo extends JpaRepository<Lesson,Long> {
}
