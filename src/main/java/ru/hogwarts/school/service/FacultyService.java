package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository,
                          StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty update(long id, Faculty faculty) {
        logger.debug("update create was invoked with parameters id = {}, facultyName = {}", id, faculty.getName());
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setName(faculty.getName());
                    oldFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(oldFaculty);
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Faculty delete(long id) {
        logger.debug("update delete was invoked with parameter id = {}", id);
        return facultyRepository.findById(id)
                .map(faculty -> {
                    facultyRepository.delete(faculty);
                    return faculty;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Faculty get(long id) {
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public List<Faculty> findByColor(String color) {
        logger.info("Was invoked method findByColor");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findNameOrColor(String colorOrName) {
        logger.info("Was invoked method findNameOrColor");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName);
    }

    public List<Student> findStudents(long id) {
        logger.info("Was invoked method findStudents");
        Faculty faculty = get(id);
        return studentRepository.findByFaculty_Id(faculty.getId());
    }
}
