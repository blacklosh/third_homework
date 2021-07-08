package ru.itis.models;

import java.util.Objects;

public class Lesson {
    private Integer id;
    private String name;
    private String date;
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) &&
                Objects.equals(name, lesson.name) &&
                Objects.equals(date, lesson.date) &&
                Objects.equals(course, lesson.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, course);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", course=" + course +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lesson(Integer id, String name, String date, Course course) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.course = course;
    }

    public Lesson(String name, String date, Course course) {
        this.name = name;
        this.date = date;
        this.course = course;
    }
}
