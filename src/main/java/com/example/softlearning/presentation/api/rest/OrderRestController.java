package com.example.softlearning.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softlearning.core.entity.order.appservices.OrderServices;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@RestController
@RequestMapping("/softlearning/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderRestController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "/{ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonOrderByRef(@PathVariable(value = "ref") String ref) {
        try {
            return ResponseEntity.ok(orderServices.getByIdToJson(ref));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{ref}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlOrderByRef(@PathVariable(value = "ref") String ref) {
        try {
            return ResponseEntity.ok(orderServices.getByIdToXml(ref));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newOrderFromJson(@RequestBody String orderdata) {
        try {
            return ResponseEntity.ok(orderServices.addFromJson(orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newOrderFromXml(@RequestBody String orderdata) {
        try {
            return ResponseEntity.ok(orderServices.addFromXml(orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{ref}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOrderFromJson(@PathVariable(value = "ref") String ref, @RequestBody String orderdata) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromJson(ref, orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{ref}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateOrderFromXml(@PathVariable(value = "ref") String ref, @RequestBody String orderdata) {
        try {
            return ResponseEntity.ok(orderServices.updateOneFromXml(ref, orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{ref}")
    public ResponseEntity<String> deleteByRef(@PathVariable(value = "ref") String ref) {
        try {
            orderServices.deleteById(ref);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}