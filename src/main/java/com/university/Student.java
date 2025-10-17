package com.university;

public class Student extends Person {
    private String email;

    public Student(String name, String address, String email) {
        super(name, address);   // inicializa Person
        this.email = email;     // inicializa Student
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getName() + " (" + email + ")";
    }

    // equals y hashCode basados en email (para que el mismo alumno no se duplique)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student that = (Student) o;
        return email != null && email.equalsIgnoreCase(that.email);
    }

    @Override
    public int hashCode() {
        return email == null ? 0 : email.toLowerCase().hashCode();
    }
}
