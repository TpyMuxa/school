package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student update(long id, Student student) {
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setName(student.getName());
                    oldStudent.setAge(student.getAge());
                    return studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student delete(long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return student;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student get(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<Student> find(int age) {
        return studentRepository.findByAge(age);
    }
}
