package ru.hogwarts.school.service;

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

    public FacultyService(FacultyRepository facultyRepository,
                          StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty create(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty update(long id, Faculty faculty) {
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setName(faculty.getName());
                    oldFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(oldFaculty);
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Faculty delete(long id) {
        return facultyRepository.findById(id)
                .map(faculty -> {
                    facultyRepository.delete(faculty);
                    return faculty;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Faculty get(long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findNameOrColor(String colorOrName) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName);
    }

    public List<Student> findStudents(long id) {
        Faculty faculty = get(id);
        return studentRepository.findByFaculty_Id(faculty.getId());
    }
}
