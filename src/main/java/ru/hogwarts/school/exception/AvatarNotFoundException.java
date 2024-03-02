package ru.hogwarts.school.exception;

public class AvatarNotFoundException extends NotFoundException {

    private final long studentId;

    public AvatarNotFoundException(long id) {
        this.studentId = id;
    }

    @Override
    public String getMessage() {

        return "Аватар для студента с id = %d не найден".formatted(studentId);
    }
}
