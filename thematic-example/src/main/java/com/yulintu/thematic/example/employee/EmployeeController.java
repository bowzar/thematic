package com.yulintu.thematic.example.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private ServiceEmployee service;

    @GetMapping("/echo/{val}")
    public String echo(@PathVariable String val) {
        return service.echo(val);
    }

    @GetMapping("/all")
    public List<User> all() {
        return service.all();
    }
}
