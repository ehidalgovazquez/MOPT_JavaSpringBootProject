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

import com.example.softlearning.core.entity.order.appservices.OrderServices;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.example.softlearning.infrastructure.security.service.JwtService;

@RestController
@RequestMapping("/softlearning/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderRestController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private JwtService jwtService;

    @GetMapping(value = "/{ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJsonOrderByRef(
            @PathVariable(value = "ref") String ref,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnOrder(authentication, authorizationHeader, ref)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para acceder a esta orden");
            }
            return ResponseEntity.ok(orderServices.getByIdToJson(ref));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{ref}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlOrderByRef(
            @PathVariable(value = "ref") String ref,
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            if (isClientAndNotOwnOrder(authentication, authorizationHeader, ref)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para acceder a esta orden");
            }
            return ResponseEntity.ok(orderServices.getByIdToXml(ref));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllOrdersAsJson(
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientRole(authentication)) {
                Integer clientId = extractClientIdFromHeader(authorizationHeader);
                if (clientId == null) {
                    return ResponseEntity.status(403).body("Acceso denegado: No se pudo obtener tu id_cliente");
                }
                return ResponseEntity.ok(orderServices.getAllOrdersByClientIdToJson(clientId));
            }
            return ResponseEntity.ok(orderServices.getAllOrdersToJson());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllOrdersAsXml(
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientRole(authentication)) {
                Integer clientId = extractClientIdFromHeader(authorizationHeader);
                if (clientId == null) {
                    return ResponseEntity.status(403).body("Acceso denegado: No se pudo obtener tu id_cliente");
                }
                return ResponseEntity.ok(orderServices.getAllOrdersByClientIdToXml(clientId));
            }
            return ResponseEntity.ok(orderServices.getAllOrdersToXml());
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newOrderFromJson(
            @RequestBody String orderdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientTryingToCreateOrderForOtherClient(authentication, authorizationHeader, orderdata, "json")) {
                return ResponseEntity.status(403).body("Acceso denegado: Solo puedes crear pedidos para tu cliente");
            }
            return ResponseEntity.ok(orderServices.addFromJson(orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al crear orden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> newOrderFromXml(
            @RequestBody String orderdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientTryingToCreateOrderForOtherClient(authentication, authorizationHeader, orderdata, "xml")) {
                return ResponseEntity.status(403).body("Acceso denegado: Solo puedes crear pedidos para tu cliente");
            }
            return ResponseEntity.ok(orderServices.addFromXml(orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al crear orden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{ref}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOrderFromJson(
            @PathVariable(value = "ref") String ref,
            @RequestBody String orderdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientAndNotOwnOrder(authentication, authorizationHeader, ref)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para actualizar esta orden");
            }
            return ResponseEntity.ok(orderServices.updateOneFromJson(ref, orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al actualizar orden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{ref}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateOrderFromXml(
            @PathVariable(value = "ref") String ref,
            @RequestBody String orderdata,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientAndNotOwnOrder(authentication, authorizationHeader, ref)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para actualizar esta orden");
            }
            return ResponseEntity.ok(orderServices.updateOneFromXml(ref, orderdata));
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al actualizar orden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error en los datos: " + e.getMessage());
        }
    }

    @DeleteMapping("/{ref}")
    public ResponseEntity<String> deleteByRef(
            @PathVariable(value = "ref") String ref,
            Authentication authentication,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        try {
            if (isClientAndNotOwnOrder(authentication, authorizationHeader, ref)) {
                return ResponseEntity.status(403).body("Acceso denegado: No tienes permisos para eliminar esta orden");
            }
            orderServices.deleteById(ref);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(400).body("Error al eliminar orden: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    private boolean isClientAndNotOwnOrder(Authentication authentication, String authorizationHeader, String ref) {
        if (authentication == null || authorizationHeader == null || !authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"))) {
            return false;
        }
        Integer tokenClientId = extractClientIdFromHeader(authorizationHeader);
        if (tokenClientId == null) {
            return true;
        }
        try {
            Integer orderClientId = orderServices.getClientIdForOrder(ref);
            return !tokenClientId.equals(orderClientId);
        } catch (ServiceException e) {
            return true;
        }
    }

    private Integer extractClientIdFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return jwtService.extractClientId(authorizationHeader.substring(7));
    }

    private boolean isClientRole(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"));
    }

    private boolean isClientTryingToCreateOrderForOtherClient(Authentication authentication, String authorizationHeader,
            String orderdata, String format) {
        if (authentication == null || authorizationHeader == null || !authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"))) {
            return false;
        }
        Integer tokenClientId = extractClientIdFromHeader(authorizationHeader);
        if (tokenClientId == null) {
            return true;
        }
        Integer orderClientId = extractClientIdFromOrderData(orderdata, format);
        if (orderClientId == null) {
            return false;
        }
        return !tokenClientId.equals(orderClientId);
    }

    private Integer extractClientIdFromOrderData(String orderdata, String format) {
        try {
            if ("json".equalsIgnoreCase(format)) {
                return extractClientIdFromJson(orderdata);
            } else if ("xml".equalsIgnoreCase(format)) {
                return extractClientIdFromXml(orderdata);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Integer extractClientIdFromJson(String json) {
        try {
            int idClientIndex = json.indexOf("\"id_client\":");
            if (idClientIndex == -1) {
                idClientIndex = json.indexOf("\"idClient\":");
            }
            if (idClientIndex == -1) {
                return null;
            }
            int colonIndex = json.indexOf(":", idClientIndex);
            int commaIndex = json.indexOf(",", colonIndex);
            int braceIndex = json.indexOf("}", colonIndex);
            int endIndex = commaIndex != -1 && commaIndex < braceIndex ? commaIndex : braceIndex;
            String value = json.substring(colonIndex + 1, endIndex).trim();
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer extractClientIdFromXml(String xml) {
        try {
            int idClientStartIndex = xml.indexOf("<id_client>");
            if (idClientStartIndex == -1) {
                idClientStartIndex = xml.indexOf("<idClient>");
            }
            if (idClientStartIndex == -1) {
                return null;
            }
            int tagLength = xml.substring(idClientStartIndex).indexOf(">") + 1;
            int valueStartIndex = idClientStartIndex + tagLength;
            int valueEndIndex = xml.indexOf("</", valueStartIndex);
            String value = xml.substring(valueStartIndex, valueEndIndex).trim();
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}