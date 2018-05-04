package com.yulintu.thematic.data.sharding.test.repositories;

import com.yulintu.thematic.data.Repository;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;

import java.util.List;

public interface RepositorySJZD extends Repository {

    boolean any();

    boolean any(String code);

    int count(String code);

    List<SJZD> get(String code);
}
