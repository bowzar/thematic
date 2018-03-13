package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.web.ApiExceptionCode;

@ApiExceptionCode(value = 4343, status = 502)
public class EmployeeTestException extends RuntimeException {
    public EmployeeTestException() {
    }

    public EmployeeTestException(String message) {
        super(message);
    }

    public EmployeeTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeTestException(Throwable cause) {
        super(cause);
    }

    public EmployeeTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
