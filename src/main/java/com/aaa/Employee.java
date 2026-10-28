package com.aaa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer empId;
    private String empName;
    private String gender;
    private Integer age;
    private String dept;
    private String job;
    private Double salary;
    private Date hireDate;
    private String phone;
    private String email;
}
