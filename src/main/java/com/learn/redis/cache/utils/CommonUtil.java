package com.learn.redis.cache.utils;

import com.learn.redis.cache.model.Employee;
import com.learn.redis.cache.response.EmployeeResponse;
import lombok.extern.log4j.Log4j2;
import org.hibernate.internal.log.SubSystemLogging;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Log4j2
public class CommonUtil {


    public static int calculateAge(String dob) {
        LocalDate birthDate = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthDate = LocalDate.parse(dob, dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert birthDate != null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static EmployeeResponse entityToEmployeeResponse(Employee employee) {
        if (employee == null) {
            log.error("Employee object is null");
            return null;
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .age(employee.getAge())
                .build();
    }
}
