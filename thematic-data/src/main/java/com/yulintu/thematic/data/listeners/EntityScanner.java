package com.yulintu.thematic.data.listeners;

import com.yulintu.thematic.ListBean;
import com.yulintu.thematic.data.ClassPathXmlApplicationContextPool;
import com.yulintu.thematic.data.querydsl.EntityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class EntityScanner implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${datasource.entities.file.name:spring.application.entities.xml}")
    private String configName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ApplicationContext context = ClassPathXmlApplicationContextPool.findInitialize(configName);
        ListBean bean = context.getBean(ListBean.class);
        List<String> list = bean.getList();

        EntityContext.installAll(list.toArray(new String[0]));
    }
}
