package com.example.softlearning.core.entity.order.mappers;

import com.example.softlearning.core.entity.order.dtos.OrderDetailJpaDTO;

public class OrderDetailJpaMapper {
    public static OrderDetailJpaDTO fromString(String line) {
        String[] p = line.trim().split(",");
        return new OrderDetailJpaDTO(p[0].trim(), Integer.parseInt(p[1].trim()), 
            Double.parseDouble(p[2].trim()), Double.parseDouble(p[3].trim()));
    }
}