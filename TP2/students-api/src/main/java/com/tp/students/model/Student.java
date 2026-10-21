package com.tp.students.model;

/**
 * Modèle représentant un étudiant.
 */
public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String filiere;

    public Student() {}

    public Student(Long id, String firstName, String lastName, String email, int age, String filiere) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.filiere = filiere;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
}

