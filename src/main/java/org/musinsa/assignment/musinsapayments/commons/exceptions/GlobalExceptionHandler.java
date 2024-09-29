package org.musinsa.assignment.musinsapayments.commons.exceptions;

import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("오류가 발생했습니다 : {}", e.getMessage());
        return ResponseEntity
            .status(500)
            .body(ExceptionResponse.of(ExceptionsType.SERVER_ERROR));
    }

    @ExceptionHandler(PointException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(PointException e) {
        log.error("포인트 관련 오류가 발생했습니다 : {}", e.getErrorType().getMessage());
        return ResponseEntity
            .status(e.getErrorType().getStatus())
            .body(ExceptionResponse.of(e.getErrorType()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));

        log.error("유효하지 않는 파라미터입니다. : {}", errorMessage);
        return ResponseEntity
            .status(400)
            .body(ExceptionResponse.of(400, errorMessage));
    }

}
