package com.yulintu.thematic.data.sharding.test;

import com.yulintu.thematic.data.GlobalApplicationContext;
import com.yulintu.thematic.data.sharding.test.configurations.TestServiceConfiguration;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;
import com.yulintu.thematic.data.sharding.test.services.ServiceSJZD;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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

//        Arrays.asList(1, 2, 3, 4).parallelStream().forEach(c -> {
//            while (true) {
        boolean s0 = serviceSJZD.any();
        boolean s1 = serviceSJZD.any("51");
        boolean s2 = serviceSJZD.any("510281");
        int s3 = serviceSJZD.count("510281");
        int s4 = serviceSJZD.count("51");

        List<SJZD> s5 = serviceSJZD.get("51");
        List<SJZD> s6 = serviceSJZD.get("510281");
//            }
//        });

    }
}
