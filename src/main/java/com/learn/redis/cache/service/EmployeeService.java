package com.learn.redis.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.redis.cache.model.Employee;
import com.learn.redis.cache.repository.EmployeeRepository;
import com.learn.redis.cache.response.EmployeeResponse;
import com.learn.redis.cache.utils.CommonUtil;
import com.learn.redis.cache.utils.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RedisService redisService;

    private static final String EMPLOYEE_KEY = "Emp_";

    @Override
    public EmployeeResponse saveUser(Employee employee) throws JsonProcessingException {
        employee.setAge(CommonUtil.calculateAge(employee.getDob().toString()));
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee saved successfully....!{}", savedEmployee);

        redisService.setValueByKey(EMPLOYEE_KEY + savedEmployee.getId().toString(), savedEmployee, 30L);

        return CommonUtil.entityToEmployeeResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = redisService.getValueByKey(EMPLOYEE_KEY + id.toString(), Employee.class);
        if (employee != null) {
            log.info("Employee fetched from Redis Cache {} ", employee);
            return CommonUtil.entityToEmployeeResponse(employee);
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(CommonUtil::entityToEmployeeResponse).orElse(null);

    }

    @Override
    public String deleteEmployeeById(Long id) {
        boolean isDeleted = redisService.deleteRecordFromRedisCache(EMPLOYEE_KEY + id.toString());
        return isDeleted ? id.toString() + "is deleted successfully" : "Employee not found..!";
    }

    @Override
    public String deleteEmployeeByMultiIds(List<Long> ids) {
        List<String> listOfKeys = ids.stream().map(id -> EMPLOYEE_KEY + id).toList();
        Long deletedCount = redisService.deleteMultiRecordFromRedisCache(listOfKeys);
        return deletedCount > 0 ? deletedCount.toString() + "records deleted successfully" : "Employee not found..!";
    }


}
