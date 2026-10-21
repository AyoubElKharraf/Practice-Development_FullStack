package com.tp.students.service;

import com.tp.students.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service gérant les opérations CRUD sur les étudiants.
 * Les données sont stockées en mémoire via une ArrayList.
 */
@Service
public class StudentService {

    // Stockage en mémoire (pas de base de données)
    private final List<Student> students = new ArrayList<>();
    private long nextId = 1L;

    public StudentService() {
        // Données initiales
        students.add(new Student(nextId++, "Alice",   "Martin",   "alice.martin@etud.fr",   21, "Informatique"));
        students.add(new Student(nextId++, "Bob",     "Dupont",   "bob.dupont@etud.fr",     22, "Mathématiques"));
        students.add(new Student(nextId++, "Camille", "Bernard",  "camille.bernard@etud.fr",20, "Physique"));
        students.add(new Student(nextId++, "David",   "Leclerc",  "david.leclerc@etud.fr",  23, "Informatique"));
        students.add(new Student(nextId++, "Emma",    "Rousseau", "emma.rousseau@etud.fr",  21, "Chimie"));
    }

    /** Retourne tous les étudiants */
    public List<Student> getAllStudents() {
        return students;
    }

    /** Retourne un étudiant par son ID */
    public Optional<Student> getStudentById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    /** Ajoute un nouvel étudiant */
    public Student addStudent(Student student) {
        student.setId(nextId++);
        students.add(student);
        return student;
    }

    /** Met à jour un étudiant existant */
    public Optional<Student> updateStudent(Long id, Student updated) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setEmail(updated.getEmail());
                    existing.setAge(updated.getAge());
                    existing.setFiliere(updated.getFiliere());
                    return existing;
                });
    }

    /** Supprime un étudiant par ID */
    public boolean deleteStudent(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }
}

