package com.learn.redis.cache.controller;

import com.learn.redis.cache.model.Employee;
import com.learn.redis.cache.response.EmployeeResponse;
import com.learn.redis.cache.service.IEmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Log4j2
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    EmployeeResponse employeeResponse;

    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeResponse> saveUser(@RequestBody Employee employee) {

        try {
            employeeResponse = employeeService.saveUser(employee);
        } catch (Exception e) {
            log.error("Exception occurred while saving data.....!");
            log.error(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        employeeResponse = null;
        try {
            employeeResponse = employeeService.getEmployeeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        String s = employeeService.deleteEmployeeById(id);
        log.info("Employee deleted successfully");
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeByMultiId")
    public ResponseEntity<String> deleteEmployeeById(@RequestBody List<Long> ids) {
        String deletedEmployees = employeeService.deleteEmployeeByMultiIds(ids);
        log.info("Employee deleted with all ids  : {}", deletedEmployees);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

}
