package com.learn.redis.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.redis.cache.model.Employee;
import com.learn.redis.cache.response.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {

    EmployeeResponse saveUser(Employee employee) throws JsonProcessingException;

    EmployeeResponse getEmployeeById(Long id);


    String deleteEmployeeById(Long id);

    String deleteEmployeeByMultiIds(List<Long> ids);
}
