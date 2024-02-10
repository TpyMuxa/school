package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> map = new HashMap<>();

    private long idGenerator = 1;

    public Student create(Student student) {
        student.setId(idGenerator++);
        map.put(student.getId(), student);
        return student;
    }

    public Student update(long id, Student student) {
        if (!map.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        Student old = map.get(id);
        old.setName(student.getName());
        old.setAge(student.getAge());
        return old;
    }

    public Student delete(long id) {
        if (!map.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        return map.remove(id);
    }

    public Student get(long id) {
        if (!map.containsKey(id)) {
            throw new StudentNotFoundException(id);
        }
        return map.get(id);
    }

    public List<Student> find(int age) {
        return map.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
