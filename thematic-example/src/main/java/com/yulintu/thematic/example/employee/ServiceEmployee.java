package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.data.Service;
import com.yulintu.thematic.data.Transaction;

import java.util.List;

public interface ServiceEmployee extends Service {

    @Transaction
    String echo(String val);

    @Transaction
    List<User> all();
}
