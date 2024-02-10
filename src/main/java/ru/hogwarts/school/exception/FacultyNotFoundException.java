package ru.hogwarts.school.exception;

public class FacultyNotFoundException extends NotFoundException {

    private final long id;

    public FacultyNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Факультет с id = %d не найден".formatted(id);
    }
}
