package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializer<T> implements Serializer<T> {
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String serialize(T object) throws ServiceException {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // Class<T> se pone y se usa para cualquier tipo de objeto
    @Override
    public T deserialize(String source, Class<T> object) throws ServiceException {
        try {
            return mapper.readValue(source, object);
        }catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
