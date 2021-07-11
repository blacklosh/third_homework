package ru.itis;

import ru.itis.models.Course;
import ru.itis.models.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    List<Lesson> findAll();
    List<Lesson> findAllByLessonName(String name);
    Optional<Lesson> findById(Integer id);
    void save(Lesson lesson);
    void update(Lesson lesson);
    void delete(Lesson lesson);
}
