package com.yulintu.thematic.example.boot;

import com.yulintu.thematic.data.Repository;
import org.springframework.context.annotation.Scope;

import java.util.List;

public interface RepositoryEmployee extends Repository {

    String echo(String val);

    List<User> all();
}
