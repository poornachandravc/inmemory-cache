package com.cache.manager.service;

import com.hazelcast.config.IndexType;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.impl.PredicateBuilderImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cache.manager.repository.EmployeeRepository;
import com.cache.manager.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private Logger logger = Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    EmployeeRepository repository;

    @Autowired
    HazelcastInstance hazelcastInstance;

    IMap<UUID, Employee> map;

    @PostConstruct
    public void init() {
//        instance.getConfig().addMapConfig(new MapConfig("employee").setTimeToLiveSeconds(60));
        map = hazelcastInstance.getMap("employee");
        map.addIndex(IndexType.HASH, "company");
        logger.info("Employees cache: " + map.size());
        logger.info("------------------ Initial Employees cache size: " + map.size());
    }

    @SuppressWarnings("rawtypes")
    public Employee findByPersonId(Integer personId) {
        PredicateBuilder.EntryObject eo = new PredicateBuilderImpl().getEntryObject();
        Predicate predicate = eo.get("personId").equal(personId);
        logger.info("Employee cache find");
        Collection<Employee> ps = map.values(predicate);
        logger.info("Employee cached: " + ps);
        Optional<Employee> e = ps.stream().findFirst();
        if (e.isPresent())
            return e.get();
        logger.info("Employee cache find");
        Employee emp = repository.findByPersonId(personId);
        logger.info("Employee: " + emp);
        map.put(emp.getId(), emp);
        return emp;
    }

    @SuppressWarnings("rawtypes")
    public List<Employee> findByCompany(String company) {
        logger.info("---------- Entering search for employee get by company : {} --------" + hazelcastInstance);
        PredicateBuilder.EntryObject eo = new PredicateBuilderImpl().getEntryObject();
        Predicate predicate = eo.get("company").equal(company);
        logger.info("Employees cache find");
        Collection<Employee> ps = map.values(predicate);
        logger.info("Employees cache size: " + ps.size());
        if (ps.size() > 0) {
            logger.info("---------- Exiting search for employee get by company from cache --------");
            return new ArrayList<>(ps);
        }
        List<Employee> e = findEmployeesByCompanyFromRepository(company);
        return e;
    }

    @Transactional
    private List<Employee> findEmployeesByCompanyFromRepository(String company) {
        logger.info("---------- Entering search for employee get by company from repository --------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Employees find by repository");
        List<Employee> e = repository.findByCompany(company);
        logger.info("Employees size: " + e.size());
        e.parallelStream().forEach(it -> {
            map.putIfAbsent(it.getId(), it);
        });
        logger.info("---------- Exiting search for employee get by company from repository --------");
        return e;
    }

    /*public Employee findById(Integer id) {
        Employee e = map.get(id);
        if (e != null)
            return e;
        e = repository.findById(id).orElseThrow();
        map.put(id, e);
        return e;
    }*/

    @Transactional()
    public Employee add(Employee e) {
        e = repository.save(e);
        map.put(e.getId(), e);
        logger.info("---------- Added data into repository with id "+ e.getId());
        return e;
    }

}