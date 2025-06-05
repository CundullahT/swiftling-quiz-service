package com.swiftling.exception;

import com.swiftling.dto.wrapper.ExceptionWrapper;
import com.swiftling.dto.wrapper.ValidationExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
//    public ResponseEntity<ExceptionWrapper> handleGenericExceptions(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ExceptionWrapper.builder()
//                        .success(false)
//                        .message("Action failed: An error occurred!")
//                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .localDateTime(LocalDateTime.now())
//                        .build());
//    }
//
//    @ExceptionHandler({PhraseAlreadyExistsException.class, PhraseCanNotBeDeletedException.class})
//    public ResponseEntity<ExceptionWrapper> handleConflictExceptions(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.CONFLICT)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(PhraseNotFoundException.class)
//    public ResponseEntity<ExceptionWrapper> handlePhraseNotFoundException(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(ExternalIdNotRetrievedException.class)
//    public ResponseEntity<ExceptionWrapper> handleExternalIdNotRetrievedException(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler({UnknownStatusException.class, UnknownLanguageException.class})
//    public ResponseEntity<ExceptionWrapper> handleUnknownValueExceptions(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ExceptionWrapper> handleAccessDeniedException(Throwable exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.FORBIDDEN)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(FileNotFoundException.class)
//    public ResponseEntity<ExceptionWrapper> handleFileNotFoundException(FileNotFoundException exception) {
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<ExceptionWrapper> handleIOException(IOException exception) {
//        log.error("An unexpected I/O error occurred while accessing a file. \n {}", exception.getMessage());
//        exception.printStackTrace();
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message(exception.getMessage())
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .localDateTime(LocalDateTime.now())
//                .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionWrapper);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ExceptionWrapper> handleValidationExceptions(MethodArgumentNotValidException exception) {
//
//        log.error(exception.getMessage());
//        exception.printStackTrace();
//
//        ExceptionWrapper exceptionWrapper = ExceptionWrapper.builder()
//                .success(false)
//                .message("Invalid Input(s)")
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .localDateTime(LocalDateTime.now())
//                .build();
//
//        List<ValidationExceptionWrapper> validationExceptions = collectValidationExceptions(exception);
//
//        exceptionWrapper.setValidationExceptions(validationExceptions);
//        exceptionWrapper.setErrorCount(validationExceptions.size());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionWrapper);
//
//    }
//
//    private List<ValidationExceptionWrapper> collectValidationExceptions(MethodArgumentNotValidException exception) {
//
//        List<ValidationExceptionWrapper> validationExceptions = new ArrayList<>();
//
//        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
//
//            String fieldName = ((FieldError) error).getField();
//            Object rejectedValue = ((FieldError) error).getRejectedValue();
//            String errorMessage = error.getDefaultMessage();
//
//            ValidationExceptionWrapper validationException = ValidationExceptionWrapper.builder()
//                    .errorField(fieldName)
//                    .rejectedValue(rejectedValue)
//                    .reason(errorMessage)
//                    .build();
//
//            validationExceptions.add(validationException);
//
//        }
//
//        return validationExceptions;
//
//    }

}
