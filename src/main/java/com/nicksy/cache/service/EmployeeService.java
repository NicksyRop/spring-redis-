package com.nicksy.cache.service;

import com.nicksy.cache.entity.Employee;
import com.nicksy.cache.exception.ResourceNotFoundException;
import com.nicksy.cache.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CacheManager cacheManager;

    @Cacheable(value = "employees" , key = "#employeeId", sync = true)
    public Employee getEmployee(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found:" + employeeId));
    }
    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @CachePut(value = "employees", key = "#employee.id")
    public Employee saveEmployee(Employee employee) {
        log.info("Saving employee to the database and updating cache");
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(UUID employeeId) {
      log.info("Removing all employees from the database and cache");
        employeeRepository.deleteById(employeeId);
    }

}
