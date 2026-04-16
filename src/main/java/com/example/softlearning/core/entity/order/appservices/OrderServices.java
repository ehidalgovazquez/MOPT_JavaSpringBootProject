package com.example.softlearning.core.entity.order.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@Service
public interface OrderServices {
    public String getByIdToJson(String ref) throws ServiceException;
    public String getByIdToXml(String ref) throws ServiceException;
    public String addFromJson(String data) throws ServiceException;
    public String addFromXml(String data) throws ServiceException;
    public String updateOneFromJson(String ref, String data) throws ServiceException;
    public String updateOneFromXml(String ref, String data) throws ServiceException;
    public void deleteById(String ref) throws ServiceException;
}