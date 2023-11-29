package com.bank.reporter.exception;

/**
 * Exception Class
 */
public class ReporterException extends RuntimeException{

    public ReporterException(final String message) {
        super(message);
    }

    public ReporterException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
