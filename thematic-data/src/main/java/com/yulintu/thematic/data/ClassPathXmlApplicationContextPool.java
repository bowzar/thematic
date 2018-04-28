package com.yulintu.thematic.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPathXmlApplicationContextPool {

    //region fields
    private static final Logger logger = LoggerFactory.getLogger(ClassPathXmlApplicationContextPool.class);
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

    public static ClassPathXmlApplicationContext findInitialize(String fileName) {

        ClassPathXmlApplicationContext ac = tryInitialize(String.format("file:%s", fileName));
        if (ac == null)
            ac = tryInitialize(String.format("file:config/%s", fileName));
        if (ac == null)
            ac = tryInitialize(String.format("classpath:%s", fileName));

        if (ac == null)
            throw new RuntimeException(String.format("未找到名称为 %s 的文件", fileName));

        return ac;
    }

    private static ClassPathXmlApplicationContext tryInitialize(String fileName) {

        try {
            return initialize(fileName);

        } catch (Throwable e) {
            if (BeanDefinitionStoreException.class.isAssignableFrom(e.getClass()))
                e = e.getCause();

            if (FileNotFoundException.class.isAssignableFrom(e.getClass()))
                return null;

            logger.error(String.format("加载 Spring 配置文件的过程中发生了异常：%s", e), e);
            return null;
        }
    }
    //endregion
}
