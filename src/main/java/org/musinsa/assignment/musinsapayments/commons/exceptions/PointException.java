package org.musinsa.assignment.musinsapayments.commons.exceptions;

import lombok.Getter;

@Getter
public class PointException extends RuntimeException {
    private final ExceptionsType exceptionsType;

    public PointException(ExceptionsType exceptionsType) {
        this.exceptionsType = exceptionsType;
    }

    public ExceptionsType getErrorType() {
        return exceptionsType;
    }
}
