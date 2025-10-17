package com.university;

import java.util.Objects;

public class Course {
    private String teacher;
    private String subject;
    private int classroom;

    public Course(String teacher, String subject, int classroom) {
        this.teacher = teacher;
        this.subject = subject;
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public int getClassroom() {
        return classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return classroom == course.classroom &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(subject, course.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, subject, classroom);
    }

    @Override
    public String toString() {
        return subject + " (" + teacher + ", aula " + classroom + ")";
    }
}