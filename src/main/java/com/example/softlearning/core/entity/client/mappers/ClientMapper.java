package com.example.softlearning.core.entity.client.mappers;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.client.model.Client;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public class ClientMapper {
    public static ClientDTO ClientToDTO(Client c){
        return new ClientDTO(
            c.getIdClient(), 
            c.getIdPerson(), 
            c.getEmail(), 
            c.getPhone(), 
            c.getAddress(), 
            c.getNamePerson(), 
            c.getRegistrationDate()
        );
    }

    public static Client DTOToClient(ClientDTO dto) throws BuildException {
        return Client.getInstance(
            dto.getIdPerson(),
            dto.getEmail(),
            dto.getPhone(),
            dto.getAddress(),
            dto.getNamePerson(),
            dto.getIdClient(),
            dto.getRegistrationDate()
        );
    }
}