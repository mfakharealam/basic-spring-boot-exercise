package com.learning.student;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        verifyIfStudentEmailAlreadyExists(student.getEmail());
        studentRepository.save(student);
    }

    private void verifyIfStudentEmailAlreadyExists(String email) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
    }

    public void deleteStudentById(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "Student with id " + studentId + " does not exist!"
        ));
        if (StringUtils.isNotBlank(name) && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (StringUtils.isNotBlank(email) && !Objects.equals(student.getEmail(), email)) {
            verifyIfStudentEmailAlreadyExists(email);
            student.setEmail(email);
        }
    }
}
