package com.example.softlearning.core.entity.employee.domainservices;

import org.springframework.stereotype.Service;

@Service // Service realmente es un componente gestionado por Spring
public class EmployeeJSONService implements EmployeeService {

    @Override
    public String serialize() {
        return "{'name': 'eric'}";
    }

}
