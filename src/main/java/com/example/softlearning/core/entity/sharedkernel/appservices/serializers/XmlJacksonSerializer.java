package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlJacksonSerializer<T> implements Serializer<T>{
    private XmlMapper mapper = new XmlMapper();
    
    @Override
    public String serialize(T object) throws ServiceException {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    @Override
    public T deserialize(String source, Class<T> object) throws ServiceException {
        try {
            return mapper.readValue(source, object);
        }catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}