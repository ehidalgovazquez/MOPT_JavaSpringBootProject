package com.example.softlearning.core.entity.client.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.client.mappers.ClientMapper;
import com.example.softlearning.core.entity.client.persistence.ClientRepository;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@Controller
public class ClientServicesImpl implements ClientServices {

    @Autowired
    private ClientRepository clientRepository;
    private Serializer<ClientDTO> serializer;

    // Implementando métodos auxiliares protegidos que son mas específicos

    protected ClientDTO getDTOById(int id)  {
        return clientRepository.findById(id).orElse(null);
    }

    protected ClientDTO getById(int id) throws ServiceException {
        ClientDTO bdto = this.getDTOById(id);

        if ( bdto == null ) {
            throw new ServiceException("client " + id + " not found");
        }
        
        return bdto;
    }
    
    protected ClientDTO checkInputData(String client) throws ServiceException {
        try {
            ClientDTO bdto = (ClientDTO) this.serializer.deserialize(client, ClientDTO.class);
            ClientMapper.DTOToClient(bdto);
            return bdto;
        } catch (BuildException e) {
            throw new ServiceException("error in the input client data: " + e.getMessage());
        }
    }

    protected ClientDTO addClient(String client) throws ServiceException {
        ClientDTO bdto = this.checkInputData(client);
          
        if (this.getDTOById(bdto.getIdClient()) == null) {
            return clientRepository.save(bdto);
        } 
        
        throw new ServiceException("client " + bdto.getIdClient() + " already exists");
    }

    protected ClientDTO updateClient(String client) throws ServiceException {
        ClientDTO bdto = this.checkInputData(client);
        this.getById(bdto.getIdClient());
        return clientRepository.save(bdto);
    }


    // Implementando los métodos de la interfaz

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.JSON_CLIENT).serialize(this.getById(id));
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.XML_CLIENT).serialize(this.getById(id));
    }
    
    @Override
    public String addFromJson(String client) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
        return serializer.serialize(this.addClient(client));
    }

    @Override
    public String addFromXml(String client) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
        return serializer.serialize(this.addClient(client));
    }

    @Override
    public String updateOneFromJson(String client) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
        return serializer.serialize(this.updateClient(client));
    }

    @Override
    public String updateOneFromXml(String client) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
        return serializer.serialize(this.updateClient(client));
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        this.getById(id);
        clientRepository.deleteById(id);
    }
}