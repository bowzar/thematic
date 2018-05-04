package com.yulintu.thematic;

public class ExceptionUtils {

    public static void throwRuntimeException(Throwable e) {
        if (e instanceof RuntimeException)
            throw (RuntimeException) e;
        else
            throw new RuntimeException(e);
    }

    public static RuntimeException createRuntimeException(Throwable e) {

        if (RuntimeException.class.isAssignableFrom(e.getClass()))
            return (RuntimeException) e;

        return new RuntimeException(e);
    }
}
