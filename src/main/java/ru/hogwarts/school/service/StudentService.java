package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        student.setId(null);
        fillFaculty(student.getFaculty(), student);
        return studentRepository.save(student);
    }

    public Student update(long id, Student student) {
        logger.debug("update create was invoked with parameters id = {}, studentName = {}", id, student.getName());
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setName(student.getName());
                    oldStudent.setAge(student.getAge());
                    fillFaculty(student.getFaculty(), oldStudent);
                    return studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student delete(long id) {
        logger.debug("update delete was invoked with parameter id = {}", id);
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return student;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student get(long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<Student> findByAge(int age) {
        logger.info("Was invoked method findByAge class StudentService");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method findByAgeBetween class StudentService");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method findFaculty class StudentService");
        return get(id).getFaculty();
    }

    private void fillFaculty(Faculty faculty, Student student) {
        logger.info("Was invoked method fillFaculty class StudentService");
        if (faculty != null && faculty.getId() != null) {
            Faculty facultyFromDb = facultyRepository.findById(faculty.getId())
                    .orElseThrow(() -> new FacultyNotFoundException(faculty.getId()));
            student.setFaculty(facultyFromDb);
        }
    }

    public int getCountOfStudents() {
        logger.info("Was invoked method getCountOfStudents class StudentService");
        return studentRepository.getCountOfStudents();
    }

    public double getAverageAgeOfStudents() {
        logger.info("Was invoked method getAverageAgeOfStudents class StudentService");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastNStudents(int count) {
        logger.info("Was invoked method getLastNStudents class StudentService");
        return studentRepository.getLastNStudents(count);
    }
}
