package com.yulintu.thematic.data.hibernate.test;

import com.yulintu.thematic.data.GlobalApplicationContext;
import com.yulintu.thematic.data.hibernate.test.configurations.TestServiceConfiguration;
import com.yulintu.thematic.data.hibernate.test.services.ServiceSJZD;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class TestServiceSJZD {

    @Autowired
    private ServiceSJZD serviceSJZD;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void before() {
        GlobalApplicationContext.setApplicationContext(applicationContext);
    }

    @Test
    public void testCommon() {
        boolean s0 = serviceSJZD.any();
    }
}
