package com.example.softlearning.core.entity.book.appservices;

import org.springframework.stereotype.Service;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@Service
public interface BookServices {
    public String getAllToJson() throws ServiceException;
    public String getAllToXml() throws ServiceException;
    public String getByIdToJson(int id) throws ServiceException;
    public String getByIdToXml(int id) throws ServiceException;
    public String addFromJson(String book) throws ServiceException;
    public String addFromXml(String book) throws ServiceException;
    public String updateOneFromJson(String book) throws ServiceException;
    public String updateOneFromXml(String book) throws ServiceException;
    public void deleteById(int id) throws ServiceException;
    public String findByFiltersToJson(String name, String author, Double maxPrice) throws ServiceException;
    public String findByFiltersToXml(String name, String author, Double maxPrice) throws ServiceException;
}