package com.example.softlearning.core.entity.order.appservices;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailJpaDTO;
import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.example.softlearning.core.entity.order.mappers.OrderJpaMapper;
import com.example.softlearning.core.entity.order.mappers.OrderMapper;
import com.example.softlearning.core.entity.order.model.Order;
import com.example.softlearning.core.entity.order.persistence.OrderRepository;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.example.softlearning.infrastructure.persistence.jpa.JpaBookRepository;
import com.example.softlearning.infrastructure.persistence.jpa.JpaClientRepository;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private JpaBookRepository bookRepository;
    
    @Autowired
    private JpaClientRepository clientRepository;
    
    private Serializer<OrderDTO> serializer;

    protected OrderDTO getDTOByRef(String ref) {
        return orderRepository.findById(ref)
                .map(jpa -> OrderMapper.OrderToDTO(jpa))
                .orElse(null);
    }

    protected OrderDTO getByRef(String ref) throws ServiceException {
        OrderDTO dto = this.getDTOByRef(ref);
        if (dto == null) {
            throw new ServiceException("Order " + ref + " not found");
        }
        return dto;
    }

    protected OrderDTO checkInputData(String orderData) throws ServiceException {
        try {
            OrderDTO dto = (OrderDTO) this.serializer.deserialize(orderData, OrderDTO.class);
            OrderMapper.DTOToOrder(dto); 
            return dto;
        } catch (BuildException e) {
            throw new ServiceException("Error in the input order data: " + e.getMessage());
        }
    }

    @Transactional
    protected OrderDTO addOrder(String orderData) throws ServiceException {
        OrderDTO dto = this.checkInputData(orderData);
        if (this.getDTOByRef(dto.getRef()) == null) {
            try {
                Order model = OrderMapper.DTOToOrder(dto);
                OrderJpaDTO jpa = OrderJpaMapper.toJpaEntity(model);
                
                if (!clientRepository.existsById(jpa.getIdClient())) {
                    throw new ServiceException("Client with id " + jpa.getIdClient() + " not found");
                }
                
                for (OrderDetailJpaDTO detail : jpa.getShopcartDetails()) {
                    if (!bookRepository.existsById(detail.getBookId())) {
                        throw new ServiceException("Book with id " + detail.getBookId() + " not found");
                    }
                }
                
                return OrderMapper.OrderToDTO(
                    orderRepository.save(jpa)
                );
            } catch (BuildException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        throw new ServiceException("Order " + dto.getRef() + " already exists");
    }

    @Transactional
    protected OrderDTO updateOrder(String ref, String orderData) throws ServiceException {
        OrderJpaDTO existing = orderRepository.findById(ref).orElse(null);
        if (existing == null) {
            throw new ServiceException("Order " + ref + " not found");
        }
        OrderDTO dto = this.checkInputData(orderData);

        String existingStatus = existing.getStatus();
        String incomingStatus = dto.getStatus();
        
        if(incomingStatus != null ) {
            if(incomingStatus.equals("FINISHED")){
                throw new ServiceException("Cannot change order data from status FINISHED");
            }
            
            if(!"CREATED".equals(incomingStatus) && !"CANCELLED".equals(incomingStatus) && !"CONFIRMED".equals(incomingStatus) && !"FORTHCOMMING".equals(incomingStatus) && !"DELIVERED".equals(incomingStatus) && !"FINISHED".equals(incomingStatus)) {
                throw new ServiceException("Invalid status value: " + incomingStatus + "");
            }

            if (!existingStatus.equals(incomingStatus)) {
                throw new ServiceException("Cannot change order status from " + existingStatus + " to " + incomingStatus);
            }
        }

        String errorMessage = "";
        if(!existingStatus.equals("CREATED")) {
            if(!dto.getRef().equals(existing.getRef())) {
                errorMessage += " reference,";
            }

            if(dto.getIdClient() != existing.getIdClient()) {
                errorMessage += " idClient,";
            }

            if (!Objects.equals(dto.getStartDate(), existing.getStartDate())) {
                errorMessage += " startDate,";
            }

            if (!Objects.equals(dto.getDescription(), existing.getDescription())) {
                errorMessage += " description,";
            }

            if (!Objects.equals(dto.getAddress(), existing.getAddress())) {
                errorMessage += " address,";
            }

            if (!Objects.equals(dto.getName(), existing.getName())) {
                errorMessage += " name,";
            }

            if (!Objects.equals(dto.getPhone(), existing.getPhone())) {
                errorMessage += " phone,";
            }

            if(!shopcartDetailsMatch(dto.getShopcartDetails(), existing.getShopcartDetails())) {
                errorMessage += " shopcartDetails,";
            }
            
            if(!datesMatch(dto.getPaymentDate(), existing.getPaymentDate())) {
                errorMessage += " paymentDate,";
            }

            if (!Objects.equals(dto.getPhysicalData(), existing.getPhysicalData()) && !existingStatus.equals("CONFIRMED")) {
                errorMessage += " physicalData,";
            }

            if (!datesMatch(dto.getDeliveryDate(), existing.getDeliveryDate()) && !existingStatus.equals("FORTHCOMMING")) {
                errorMessage += " deliveryDate,";
            }

            if (!datesMatch(dto.getFinishDate(), existing.getFinishDate()) && !existingStatus.equals("DELIVERED")) {
                errorMessage += " finishDate,";
            }

            if(!Objects.equals(dto.getStatus(), existing.getStatus())) {
                errorMessage += " status,";
            }
        }

        if(!errorMessage.equals("")) {
            throw new ServiceException("Cannot modify" + errorMessage + "for order in status " + existingStatus);
        }
        
        try {
            Order model = OrderMapper.DTOToOrder(dto);
            OrderJpaDTO incomingJpa = OrderJpaMapper.toJpaEntity(model);
            
            if (!clientRepository.existsById(incomingJpa.getIdClient())) {
                throw new ServiceException("Client with id " + incomingJpa.getIdClient() + " not found");
            }
            
            for (OrderDetailJpaDTO detail : incomingJpa.getShopcartDetails()) {
                if (!bookRepository.existsById(detail.getBookId())) {
                    errorMessage += detail.getBookId() + ", ";
                }
                detail.setOrder(incomingJpa);
            }

            if(!errorMessage.equals("")) {
                throw new ServiceException("Books with id " + errorMessage + "not found");
            }

            return OrderMapper.OrderToDTO(
                orderRepository.save(incomingJpa)
            );

        } catch (BuildException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String getByIdToJson(String ref) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.JSON_ORDER).serialize(this.getByRef(ref));
    }

    @Override
    public String getByIdToXml(String ref) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.XML_ORDER).serialize(this.getByRef(ref));
    }

    @Override
    public Integer getClientIdForOrder(String ref) throws ServiceException {
        return this.getByRef(ref).getIdClient();
    }

    @Override
    public String addFromJson(String orderData) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
        return serializer.serialize(this.addOrder(orderData));
    }

    @Override
    public String addFromXml(String orderData) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
        return serializer.serialize(this.addOrder(orderData));
    }

    @Override
    public String updateOneFromJson(String ref, String orderData) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
        return serializer.serialize(this.updateOrder(ref, orderData));
    }

    @Override
    public String updateOneFromXml(String ref, String orderData) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
        return serializer.serialize(this.updateOrder(ref, orderData));
    }

    @Transactional
    @Override
    public void deleteById(String ref) throws ServiceException {
        try {
            OrderJpaDTO order = orderRepository.findById(ref).orElseThrow(() -> new ServiceException("Orden no encontrada: " + ref));
            
            orderRepository.deleteOrderDetails(ref);
            orderRepository.deleteById(ref);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar la orden: " + e.getMessage());
        }
    }

    @Override
    public String getAllOrdersToJson() throws ServiceException {
        try {
            List<OrderJpaDTO> allOrders = orderRepository.findAll();
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER)
                    .serialize(allOrders.stream()
                            .map(OrderMapper::OrderToDTO)
                            .toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving all orders: " + e.getMessage());
        }
    }

    @Override
    public String getAllOrdersToXml() throws ServiceException {
        try {
            List<OrderJpaDTO> allOrders = orderRepository.findAll();
            return SerializersCatalog.getInstance(Serializers.XML_ORDER)
                    .serialize(allOrders.stream()
                            .map(OrderMapper::OrderToDTO)
                            .toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving all orders: " + e.getMessage());
        }
    }

    @Override
    public String getAllOrdersByClientIdToJson(Integer clientId) throws ServiceException {
        try {
            List<OrderJpaDTO> clientOrders = orderRepository.findByIdClient(clientId);
            return SerializersCatalog.getInstance(Serializers.JSON_ORDER).serialize(clientOrders.stream().map(OrderMapper::OrderToDTO).toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving orders for client " + clientId + ": " + e.getMessage());
        }
    }

    @Override
    public String getAllOrdersByClientIdToXml(Integer clientId) throws ServiceException {
        try {
            List<OrderJpaDTO> clientOrders = orderRepository.findByIdClient(clientId);
            return SerializersCatalog.getInstance(Serializers.XML_ORDER).serialize(clientOrders.stream().map(OrderMapper::OrderToDTO).toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving orders for client " + clientId + ": " + e.getMessage());
        }
    }

    private boolean datesMatch(String dateString, LocalDateTime dateTime) {
        if (dateString == null && dateTime == null) {
            return true;
        }

        if (dateString == null || dateTime == null) {
            return false;
        }

        try {
            LocalDateTime parsed = LocalDateTime.parse(dateString, DATE_FORMATTER);
            return parsed.equals(dateTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean shopcartDetailsMatch(List<OrderDetailDTO> dtoDetails, List<OrderDetailJpaDTO> existingDetails) {
        if (dtoDetails == null && existingDetails == null) return true;
        if (dtoDetails == null || existingDetails == null) return false;
        if (dtoDetails.size() != existingDetails.size()) return false;

        for (OrderDetailDTO dtoDetail : dtoDetails) {
            boolean foundMatch = existingDetails.stream().anyMatch(existingDetail -> 
                existingDetail.getBookId() == dtoDetail.getBookId() &&
                existingDetail.getAmount() == dtoDetail.getAmount() &&
                Double.compare(existingDetail.getPrice(), dtoDetail.getPrice()) == 0
            );
            
            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}