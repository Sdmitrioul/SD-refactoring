package ru.akirakozov.sd.refactoring.exception;

import java.io.IOException;

public class DaoException extends IOException {
    public DaoException(final String message) {
        super(message);
    }
}
