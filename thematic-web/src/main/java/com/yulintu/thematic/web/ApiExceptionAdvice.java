package com.yulintu.thematic.web;

import com.google.common.base.Strings;
import com.yulintu.thematic.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseBodyData> onException(Exception exception, HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) {

        ResponseBodyData error = new ResponseBodyData();
        int status = 500;

        String exceptionMessage = exception.getMessage();

        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            status = apiException.getStatus();
            error.setMessage(exceptionMessage);

        } else {

            ResponseStatus annotation = AnnotationUtils.
                    getClassAnnotationByType(exception.getClass(), ResponseStatus.class);

            if (annotation == null)
                annotation = handlerMethod.getMethod().getAnnotation(ResponseStatus.class);

            if (annotation != null)
                status = annotation.value().value();

            String msg = annotation != null ? msg = annotation.reason() : "";

            error.setMessage(
                    Strings.isNullOrEmpty(msg) ?
                            exceptionMessage :
                            String.format("%s, %s", msg, exceptionMessage));
        }

        error.setStatus(status);
        ResponseEntity<ResponseBodyData> responseEntity =
                new ResponseEntity<>(error, HttpStatus.valueOf(status));

        return responseEntity;
    }
//
//    private int getStatus(HttpServletRequest request) {
//        Object statusCode = request.getAttribute("javax.servlet.error.status_code");
//        return statusCode == null ? 500 : (int) statusCode;
//    }

}