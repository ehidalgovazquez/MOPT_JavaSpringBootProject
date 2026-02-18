package com.example.softlearning.core.entity.employee.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.core.entity.employee.domainservices.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired  // Autoinjección del servicio de empleado
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String serialize() {
        return employeeService.serialize();
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
