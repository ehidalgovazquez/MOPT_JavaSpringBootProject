package com.example.softlearning.core.entity.order.mappers;

import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.core.entity.order.model.Order;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public class OrderMapper {
    public static OrderDTO OrderToDTO(Order o) {
        List<OrderDetailDTO> details = new ArrayList<>();
        String raw = o.getShopcartDetails();
        if (raw != null && !raw.isEmpty()) {
            for (String line : raw.split(";")) {
                details.add(OrderDetailMapper.fromString(line));
            }
        }
        
        return new OrderDTO(o.getRef(), o.getIdClient(), o.getStartDate(), o.getDescription(), 
            o.getAddress(), o.getName(), o.getPhoneContact(), details, 
            o.getPaymentDate(), o.getPhysicalData(), o.getDeliveryDate(), o.getFinishDate());
    }

    public static Order DTOToOrder(OrderDTO dto) throws BuildException {
        StringBuilder sb = new StringBuilder();
        if (dto.getShopcartDetails() != null) {
            for (OrderDetailDTO d : dto.getShopcartDetails()) {
                sb.append(d.getBookId()).append(",").append(d.getAmount()).append(",")
                  .append(d.getPrice()).append(",").append(d.getDiscount()).append(";");
            }
        }
        
        return Order.getInstance(dto.getRef(), dto.getIdClient(), dto.getStartDate(), dto.getDescription(), 
            dto.getAddress(), dto.getName(), dto.getPhone(), sb.toString(), 
            dto.getPaymentDate(), dto.getPhysicalData(), dto.getDeliveryDate(), dto.getFinishDate());
    }
}