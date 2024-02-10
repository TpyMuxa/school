package ru.hogwarts.school.exception;

public class StudentNotFoundException extends NotFoundException {

    private final long id;

    public StudentNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Студент с id = %d не найден".formatted(id);
    }
}
