package com.yulintu.thematic;

import org.springframework.util.Assert;

public class AssertUtils {

    public static void notNull(Object parameter, String parameterName) {
        Assert.notNull(parameter, String.format("名称为 %s 的参数不能为 null。", parameterName));
    }

    public static void ifFalse(boolean val, String message) {
        if (!val)
            throw new RuntimeException(message);
    }

    public static void ifTrue(boolean val, String message) {
        if (val)
            throw new RuntimeException(message);
    }
}
