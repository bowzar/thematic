package com.yulintu.thematic.web;

import com.yulintu.thematic.AnnotationHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvices {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Error> onException(Exception exception, HttpServletResponse response) {

        Error error = new Error();
        int status = 500;

        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            error.setErr(apiException.getErrorCode());
            error.setTip(exception.getMessage());

        } else {

            ApiExceptionCode annotation = AnnotationHelper.
                    getClassAnnotationByType(exception.getClass(), ApiExceptionCode.class);

            if (annotation != null)
                status = annotation.status();

            error.setErr(annotation == null ? 0L : annotation.value());
            error.setTip(annotation == null ?
                    String.format("发生了一个未知异常, %s", exception.getMessage()) :
                    exception.getMessage());
        }

        ResponseEntity<Error> responseEntity =
                new ResponseEntity<>(error, HttpStatus.valueOf(status));

        return responseEntity;
    }

    @Setter
    @Getter
    class Error {
        private Long err;
        private String tip;
    }
}