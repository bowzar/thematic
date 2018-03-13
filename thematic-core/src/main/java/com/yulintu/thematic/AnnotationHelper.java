package com.yulintu.thematic;

import java.lang.annotation.Annotation;

public class AnnotationHelper {

    public static <T extends Annotation> T getClassAnnotationByType(Class<?> type, Class<T> annotationType) {
        T[] annotations = type.getAnnotationsByType(annotationType);
        return annotations == null || annotations.length == 0 ? null : annotations[0];
    }
}
