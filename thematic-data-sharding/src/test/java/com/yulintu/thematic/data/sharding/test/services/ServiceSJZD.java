package com.yulintu.thematic.data.sharding.test.services;

import com.yulintu.thematic.data.Service;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;

import java.util.List;

public interface ServiceSJZD extends Service {

    boolean any();

    boolean any(String code);

    int count(String code);

    List<SJZD> get(String code);
}
