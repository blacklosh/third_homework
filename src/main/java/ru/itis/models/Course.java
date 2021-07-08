package ru.itis.models;

import java.util.List;
import java.util.Objects;

public class Course {
    private Integer id;
    private String name;
    private String date;
    private Integer teacherId;
    private List<Student> students;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", teacherId=" + teacherId +
                ", students=" + students +
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

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course(Integer id, String name, String date, Integer teacherId, List<Student> students) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.teacherId = teacherId;
        this.students = students;
    }

    public Course(String name, String date, Integer teacherId, List<Student> students) {
        this.name = name;
        this.date = date;
        this.teacherId = teacherId;
        this.students = students;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            Course other = (Course)obj;
            return this.id.equals(other.id) && this.name.equals(other.name) && this.date.equals(other.date) && this.teacherId.equals(other.teacherId);
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, teacherId);
    }
}
