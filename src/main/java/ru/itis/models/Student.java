package ru.itis.models;

import java.util.List;

public class Student {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer group_id;
    private List<Course> courses;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", group_id=" + group_id +
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

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Student(Integer id, String first_name, String last_name, Integer group_id, List<Course> courses) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_id = group_id;
        this.courses = courses;
    }

    public Student(String first_name, String last_name, Integer group_id, List<Course> courses) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_id = group_id;
        this.courses = courses;
    }
}
