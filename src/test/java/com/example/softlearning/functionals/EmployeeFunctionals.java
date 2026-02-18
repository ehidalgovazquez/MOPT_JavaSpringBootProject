package com.example.softlearning.functionals;

import com.example.softlearning.core.entity.employee.appservices.EmployeeController;
import com.example.softlearning.core.entity.employee.domainservices.EmployeeJSONService;

public class EmployeeFunctionals {
    public static void main(String[] args) {
		EmployeeController employeeController = new EmployeeController(new EmployeeJSONService());
		System.out.println(employeeController.serialize());
	}
}