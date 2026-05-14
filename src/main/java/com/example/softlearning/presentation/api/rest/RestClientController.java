package com.example.softlearning.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.softlearning.core.entity.client.appservices.ClientServices;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.example.softlearning.infrastructure.security.service.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
@RequestMapping("/softlearning/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class RestClientController {

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private JwtService jwtService;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonClientById(
            @PathVariable(value = "id") Integer id,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnResource(authentication, authorizationHeader, id)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para acceder a este cliente");
            }
            return ResponseEntity.ok(clientServices.getByIdToJson(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlClientById(
            @PathVariable(value = "id") Integer id,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnResource(authentication, authorizationHeader, id)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para acceder a este cliente");
            }
            return ResponseEntity.ok(clientServices.getByIdToXml(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllClientsJson() {
        try {
            return ResponseEntity.ok(clientServices.getAllToJson());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al obtener clientes: " + e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllClientsXml() {
        try {
            return ResponseEntity.ok(clientServices.getAllToXml());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al obtener clientes: " + e.getMessage());
        }
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newClientFromJson(
            @RequestBody String clientdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientTryingToCreateOtherClient(authentication, authorizationHeader, clientdata)) {
                return ResponseEntity.status(403).body("Acceso denegado: Solo puedes crear tu propio cliente");
            }
            return ResponseEntity.ok(clientServices.addFromJson(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al crear cliente: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newClientFromXml(
            @RequestBody String clientdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientTryingToCreateOtherClient(authentication, authorizationHeader, clientdata)) {
                return ResponseEntity.status(403).body("Acceso denegado: Solo puedes crear tu propio cliente");
            }
            return ResponseEntity.ok(clientServices.addFromXml(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al crear cliente: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClientFromJson(
            @PathVariable(value = "id") Integer id,
            @RequestBody String clientdata,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnResource(authentication, authorizationHeader, id)) {
                return ResponseEntity.status(403).body("Acceso denegado: No puedes actualizar este cliente");
            }
            return ResponseEntity.ok(clientServices.updateOneFromJson(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al actualizar cliente: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateClientFromXml(
            @PathVariable(value = "id") Integer id,
            @RequestBody String clientdata,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnResource(authentication, authorizationHeader, id)) {
                return ResponseEntity.status(403).body("Acceso denegado: No puedes actualizar este cliente");
            }
            return ResponseEntity.ok(clientServices.updateOneFromXml(clientdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al actualizar cliente: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable(value = "id") Integer id) {
        try {
            clientServices.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al eliminar cliente: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    private boolean isClientAndNotOwnResource(Authentication authentication, String authorizationHeader, Integer resourceId) {
        if (authentication == null || authorizationHeader == null || !authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"))) {
            return false;
        }
        Integer tokenClientId = extractClientIdFromHeader(authorizationHeader);
        return tokenClientId == null || !tokenClientId.equals(resourceId);
    }

    private Integer extractClientIdFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return jwtService.extractClientId(authorizationHeader.substring(7));
    }

    private boolean isClientTryingToCreateOtherClient(Authentication authentication, String authorizationHeader, String clientData) {
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"))) {
            return false;
        }
        Integer tokenClientId = extractClientIdFromHeader(authorizationHeader);
        Integer requestClientId = extractIdClientFromData(clientData);
        return requestClientId != null && !requestClientId.equals(tokenClientId);
    }

    private Integer extractIdClientFromData(String clientData) {
        try {
            if (clientData.trim().startsWith("{")) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(clientData);
                if (node.has("idClient")) {
                    return node.get("idClient").asInt();
                }
            } else {
                XmlMapper mapper = new XmlMapper();
                JsonNode node = mapper.readTree(clientData.getBytes());
                if (node.has("idClient")) {
                    return node.get("idClient").asInt();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
