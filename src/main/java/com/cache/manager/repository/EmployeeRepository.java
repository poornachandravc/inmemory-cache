package com.cache.manager.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;

import com.cache.manager.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public Employee findByPersonId(Integer personId);

    public List<Employee> findByCompany(String company);

    public Employee save(Employee employee);

}