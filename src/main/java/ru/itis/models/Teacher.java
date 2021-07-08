package ru.itis.models;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer exp;
    private List<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) &&
                Objects.equals(first_name, teacher.first_name) &&
                Objects.equals(last_name, teacher.last_name) &&
                Objects.equals(exp, teacher.exp) &&
                Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, exp, courses);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", exp=" + exp +
                ", courses=" + courses +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Teacher(Integer id, String first_name, String last_name, Integer exp, List<Course> courses) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.exp = exp;
        this.courses = courses;
    }

    public Teacher(String first_name, String last_name, Integer exp, List<Course> courses) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.exp = exp;
        this.courses = courses;
    }
}
