package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id) {
        return studentService.get(id);
    }

    @GetMapping(params = "age")
    public List<Student> findByAge(@RequestParam int age) {

        return studentService.findByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public List<Student> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {

        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public Faculty findFaculty(@PathVariable long id) {

        return studentService.findFaculty(id);
    }

    @GetMapping("/count")
    public int getCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("/average-age")
    public double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/last")
    public List<Student> getLastNStudents(@RequestParam int count) {
        return studentService.getLastNStudents(count);
    }

    @GetMapping("/names")
    public List<String> getNameOfStudentsWhichStartsWith(@RequestParam char startsWith) {
        return studentService.getNameOfStudentsWhichStartsWith(startsWith);
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}
