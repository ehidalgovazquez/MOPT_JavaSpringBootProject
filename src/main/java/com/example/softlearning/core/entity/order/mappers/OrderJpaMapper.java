package com.example.softlearning.core.entity.order.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.example.softlearning.core.entity.order.dtos.OrderDetailJpaDTO;
import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.example.softlearning.core.entity.order.model.Order;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public class OrderJpaMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static OrderJpaDTO toJpaEntity(Order model) {

        OrderJpaDTO jpa = new OrderJpaDTO(
            model.getRef(), model.getIdClient(), model.getStartDate(), 
            model.getDescription(), model.getAddress(), model.getName(), 
            model.getPhoneContact(), parseDate(model.getPaymentDate()), 
            model.getPhysicalData(), 
            parseDate(model.getDeliveryDate()), parseDate(model.getFinishDate())
        );

        String raw = model.getShopcartDetails();
        if (raw != null && !raw.isEmpty()) {
            for (String line : raw.split(";")) {
                if (!line.trim().isEmpty()) {
                    jpa.addOrderDetail(OrderDetailJpaMapper.fromString(line));
                }
            }
        }
        
        return jpa;
    }

    public static Order toDomain(OrderJpaDTO jpa) throws BuildException {
        StringBuilder sb = new StringBuilder();
        if (jpa.getShopcartDetails() != null) {
            for (OrderDetailJpaDTO d : jpa.getShopcartDetails()) {
                sb.append(d.getRef()).append(",")
                  .append(d.getAmount()).append(",")
                  .append(d.getPrice()).append(",")
                  .append(d.getDiscount()).append(";");
            }
        }

        return Order.getInstance(
            jpa.getRef(), jpa.getIdClient(), jpa.getStartDate(), jpa.getDescription(), 
            jpa.getAddress(), jpa.getName(), jpa.getPhone(), sb.toString(), 
            formatDate(jpa.getPaymentDate()), jpa.getPhysicalData(), 
            formatDate(jpa.getDeliveryDate()), formatDate(jpa.getFinishDate())
        );
    }

    private static LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Bad date format: " + dateStr + ". Expected format yyyy-MM-dd HH:mm:ss", e);
        }
    }

    private static String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }
}