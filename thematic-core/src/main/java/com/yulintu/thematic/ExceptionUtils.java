package com.yulintu.thematic;

public class ExceptionUtils {

    public static void throwRuntimeException(Throwable e) {
        if (e instanceof RuntimeException)
            throw (RuntimeException) e;
        else
            throw new RuntimeException(e);
    }
}
