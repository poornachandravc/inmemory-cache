package com.cache.manager.controller;

import com.cache.manager.entity.Employee;
import com.cache.manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private Logger logger = Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    EmployeeService service;

    @GetMapping("/employees/person/{id}")
    public Employee findByPersonId(@PathVariable("id") Integer personId) {
        logger.info(String.format("findByPersonId(%d)", personId));
        return service.findByPersonId(personId);
    }

    @GetMapping("/employees/company/{company}")
    public List<Employee> findByCompany(@PathVariable("company") String company) {
        logger.info(String.format("findByCompany(%s)", company));
        return service.findByCompany(company);
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable("id") Integer id) {
        logger.info(String.format("findById(%d)", id));
        return service.findByPersonId(id);
    }

    @PostMapping("/employees")
    public Employee add(@RequestBody Employee emp) {
        logger.info(String.format("add(%s)", emp));
        return service.add(emp);
    }

}