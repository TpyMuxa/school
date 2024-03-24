package ru.hogwarts.school.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.AvatarProcessingException;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.service.StudentService;

@RestControllerAdvice
public class HogwartsExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(HogwartsExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<String> handleAvatarProcessingException(AvatarProcessingException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
