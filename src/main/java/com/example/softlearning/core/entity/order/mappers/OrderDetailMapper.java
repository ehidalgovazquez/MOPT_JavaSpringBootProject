package com.example.softlearning.core.entity.order.mappers;

import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;

public class OrderDetailMapper {
    public static OrderDetailDTO fromString(String line) {
        String[] p = line.trim().split(",");
        return new OrderDetailDTO(Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), 
            Double.parseDouble(p[2].trim()), Double.parseDouble(p[3].trim()));
    }
}