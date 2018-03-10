package com.yulintu.thematic.data;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPathXmlApplicationContextPool {

    //region fields
    private static final Map<String, ClassPathXmlApplicationContext> map = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public ClassPathXmlApplicationContextPool() {

    }
    //endregion

    //region methods
    public static boolean has(String fileName) {
        return map.containsKey(fileName);
    }

    public static ClassPathXmlApplicationContext initialize(String fileName) {

        if (has(fileName))
            return map.get(fileName);

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(fileName);
        map.put(fileName, ac);
        return ac;
    }
    //endregion
}
