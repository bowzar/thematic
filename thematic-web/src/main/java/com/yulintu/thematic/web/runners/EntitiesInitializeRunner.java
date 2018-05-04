package com.yulintu.thematic.web.runners;

import com.yulintu.thematic.ListBean;
import com.yulintu.thematic.data.ClassPathXmlApplicationContextPool;
import com.yulintu.thematic.data.querydsl.EntityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntitiesInitializeRunner implements ApplicationRunner {

    @Value("${datasource.entities.file.name:spring.application.entities.xml}")
    private String configName;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ApplicationContext context = ClassPathXmlApplicationContextPool.findInitialize(configName);
        ListBean bean = context.getBean(ListBean.class);
        List<String> list = bean.getList();

        EntityContext.installAll(list.toArray(new String[0]));
    }
}
