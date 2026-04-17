package com.example.softlearning.core.entity.order.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailJpaDTO;
import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
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
            o.getPaymentDate(), o.getPhysicalData(), o.getDeliveryDate(), o.getFinishDate(), o.getStatus());
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OrderDTO OrderToDTO(OrderJpaDTO jpa) {
        List<OrderDetailDTO> details = new ArrayList<>();
        if (jpa.getShopcartDetails() != null) {
            for (OrderDetailJpaDTO detail : jpa.getShopcartDetails()) {
                BookDTO book = detail.getBook();
                // Incluir el libro solo si está inicializado (compatible con EAGER y LAZY)
                if (book != null && Hibernate.isInitialized(book)) {
                    details.add(new OrderDetailDTO(detail.getBookId(), detail.getAmount(), detail.getPrice(), detail.getDiscount(), book));
                } else {
                    details.add(new OrderDetailDTO(detail.getBookId(), detail.getAmount(), detail.getPrice(), detail.getDiscount()));
                }
            }
        }
        
        ClientDTO client = null;
        if (jpa.getClient() != null && Hibernate.isInitialized(jpa.getClient())) {
            client = jpa.getClient();
        }
        
        return new OrderDTO(jpa.getRef(), jpa.getIdClient(), client, jpa.getStartDate(), jpa.getDescription(), 
            jpa.getAddress(), jpa.getName(), jpa.getPhone(), details, 
            formatDate(jpa.getPaymentDate()), jpa.getPhysicalData(), formatDate(jpa.getDeliveryDate()), formatDate(jpa.getFinishDate()), jpa.getStatus());
    }

    private static String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
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
            dto.getPaymentDate(), dto.getPhysicalData(), dto.getDeliveryDate(), dto.getFinishDate(), dto.getStatus());
    }

}