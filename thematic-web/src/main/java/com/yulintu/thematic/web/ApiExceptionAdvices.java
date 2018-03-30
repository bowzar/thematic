package com.yulintu.thematic.web;

import com.google.common.base.Strings;
import com.yulintu.thematic.AnnotationHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvices {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Error> onException(Exception exception, HttpServletRequest request, HttpServletResponse response) {

        Error error = new Error();
        int status = getStatus(request);

        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            status = apiException.getStatus();
            error.setMessage(exception.getMessage());

        } else {

            ResponseStatus annotation = AnnotationHelper.
                    getClassAnnotationByType(exception.getClass(), ResponseStatus.class);

            if (annotation != null)
                status = annotation.value().value();

            String msg = null;
            if (annotation != null)
                msg = annotation.reason();

            error.setMessage(Strings.isNullOrEmpty(msg) ?
                    String.format("发生了一个异常, %s", exception.getMessage()) :
                    exception.getMessage());
        }

        error.setStatus(status);
        ResponseEntity<Error> responseEntity =
                new ResponseEntity<>(error, HttpStatus.valueOf(status));

        return responseEntity;
    }

    private int getStatus(HttpServletRequest request) {
        Object statusCode = request.getAttribute("javax.servlet.error.status_code");
        return statusCode == null ? 500 : (int) statusCode;
    }

    @Setter
    @Getter
    class Error {
        private int status;
        private String message;
    }
}