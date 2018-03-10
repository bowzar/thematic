package com.yulintu.thematic.data;

import org.springframework.context.ApplicationContext;

public class GlobalApplicationContext {

    //region applicationContext
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext ac) {
        applicationContext = ac;
    }
    //endregion
}
