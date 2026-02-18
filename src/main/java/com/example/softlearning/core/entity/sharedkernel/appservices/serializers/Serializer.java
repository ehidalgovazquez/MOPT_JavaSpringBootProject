package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

public interface Serializer<T> {
    public String serialize(T object) throws ServiceException;
    public T deserialize(String source, Class<T> object) throws ServiceException; 
}
