package com.example.softlearning.core.entity.client.persistence;

import java.util.List;
import java.util.Optional;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;

public interface ClientRepository  {

    public Optional<ClientDTO> findById(int id);

    public List<ClientDTO> findAll();

    public List<ClientDTO> findByEmail(String email);
 
    public List<ClientDTO> findByPartialEmail(String email);

    public Integer countByPartialEmail(String email);
    
    public ClientDTO save(ClientDTO book);
    
    public void deleteById(int id);
}
