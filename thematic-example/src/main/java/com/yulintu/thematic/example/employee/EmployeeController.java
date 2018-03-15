package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.data.Trackable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Trackable
public class EmployeeController {

    @Autowired
    private ServiceEmployee service;

    @GetMapping("/echo/{val}")
    public String echo(@PathVariable String val) {
        return service.echo(val);
    }

    @GetMapping("/clear")
    public int clear() {
        return service.clear();
    }

    @GetMapping("/all")
    public List<User> all() {
        return service.all();
    }

    @GetMapping("/all/clear")
    public int clearAll() {
        return service.clearAll();
    }
}
