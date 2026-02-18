// package com.example.softlearning.core.entity.order.mappers;

// import com.example.softlearning.core.entity.order.dtos.OrderDTO;
// import com.example.softlearning.core.entity.order.model.Order;
// import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

// public class OrderMapper {
//     public static OrderDTO OrderToDTO(Order o){
//         return new OrderDTO(
//             o.getRef(), 
//             o.getIdClient(), 
//             o.getStartDate(), 
//             o.getDescription(), 
//             o.getAddress(), 
//             o.getName(), 
//             o.getPhoneContact(),
//             o.getShopcartDetails(),
//             o.getPaymentDate(),
//             o.getPhysicalData(),
//             o.getDeliveryDate(),
//             o.getFinishDate()
//         );
//     }

//     public static Order DTOToOrder(OrderDTO dto) throws BuildException{
//         return Order.getInstance(
//             dto.getRef(), 
//             dto.getIdClient(), 
//             dto.getStartDate(), 
//             dto.getDescription(), 
//             dto.getAddress(), 
//             dto.getName(), 
//             dto.getPhone(),
//             dto.getShopcartDetails(),
//             dto.getPaymentDate(),
//             dto.getPhysicalData(),
//             dto.getDeliveryDate(),
//             dto.getFinishDate()
//         );
//     }
// }
