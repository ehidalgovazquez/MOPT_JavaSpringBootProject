package com.example.softlearning.core.entity.order.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.mappers.OrderJpaMapper;
import com.example.softlearning.core.entity.order.mappers.OrderMapper;
import com.example.softlearning.core.entity.order.model.Order;
import com.example.softlearning.core.entity.order.persistence.OrderRepository;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderRepository orderRepository;
    
    private Serializer<OrderDTO> serializer;

    protected OrderDTO getDTOByRef(String ref) {
        return orderRepository.findById(ref)
                .map(jpa -> {
                    try {
                        return OrderMapper.OrderToDTO(OrderJpaMapper.toDomain(jpa));
                    } catch (BuildException e) {
                        return null;
                    }
                }).orElse(null);
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

    protected OrderDTO addOrder(String orderData) throws ServiceException {
        OrderDTO dto = this.checkInputData(orderData);
        if (this.getDTOByRef(dto.getRef()) == null) {
            try {
                Order model = OrderMapper.DTOToOrder(dto);
                return OrderMapper.OrderToDTO(OrderJpaMapper.toDomain(
                    orderRepository.save(OrderJpaMapper.toJpaEntity(model))
                ));
            } catch (BuildException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        throw new ServiceException("Order " + dto.getRef() + " already exists");
    }

    protected OrderDTO updateOrder(String orderData) throws ServiceException {
        OrderDTO dto = this.checkInputData(orderData);
        this.getByRef(dto.getRef()); 
        try {
            Order model = OrderMapper.DTOToOrder(dto);
            return OrderMapper.OrderToDTO(OrderJpaMapper.toDomain(
                orderRepository.save(OrderJpaMapper.toJpaEntity(model))
            ));
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
        return serializer.serialize(this.updateOrder(orderData));
    }

    @Override
    public String updateOneFromXml(String ref, String orderData) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
        return serializer.serialize(this.updateOrder(orderData));
    }

    @Override
    public void deleteById(String ref) throws ServiceException {
        this.getByRef(ref);
        orderRepository.deleteById(ref);
    }
}