package org.musinsa.assignment.musinsapayments.commons.exceptions;

public record ExceptionResponse(
    int status,
    String message
) {
    public static ExceptionResponse of(ExceptionsType exceptionsType) {
        return new ExceptionResponse(exceptionsType.getStatus(), exceptionsType.getMessage());
    }

    public static ExceptionResponse of(int status, String message) {
        return new ExceptionResponse(status, message);
    }
}
