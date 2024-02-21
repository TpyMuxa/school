package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/{id}")
    public Faculty get(@PathVariable long id) {
        return facultyService.get(id);
    }

    @GetMapping
    public List<Faculty> find(@RequestParam String color) {
        return facultyService.find(color);
    }
}
