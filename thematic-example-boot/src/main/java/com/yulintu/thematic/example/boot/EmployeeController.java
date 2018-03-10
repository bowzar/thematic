package com.yulintu.thematic.example.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private ServiceEmployee service;

    @GetMapping("/echo")
    public String echo() {
        return service.echo(this.getClass().getName());
    }

    @GetMapping("/all")
    public List<User> all() {
        return service.all();
    }
}
