package com.example.clientes.controller;

import com.example.clientes.dto.ClienteRequestDTO;
import com.example.clientes.dto.ClienteResponseDTO;
import com.example.clientes.exception.ClienteNotFoundException;
import com.example.clientes.mapper.ClientMapper;
import com.example.clientes.service.IClienteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private IClienteServiceImpl clienteService;

    @Autowired
    private ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClientes() {
        Map<String, Object> response = new HashMap<>();
        List<ClienteResponseDTO> clienteResponseDTOS = clienteService.clienteDTOs();
        response.put("data", clienteResponseDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClienteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ClienteResponseDTO clienteResponseDTO = clienteService.findById(id);
            response.put("data", clienteResponseDTO);
            return ResponseEntity.ok(response);
        } catch (ClienteNotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/cliente")
    public ResponseEntity<Map<String, Object>> getClienteByIdentificacion(@RequestParam String identificacion) {
        Map<String, Object> response = new HashMap<>();
        try {
            ClienteRequestDTO clienteRequestDTO = clienteService.findByIdentificacion(identificacion);
            response.put("data", clienteRequestDTO);
            return ResponseEntity.ok(response);
        } catch (ClienteNotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/nombre")
    public ResponseEntity<Map<String, Object>> getClienteByName(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        try {
            ClienteRequestDTO clienteRequestDTO = clienteService.findByNombre(nombre);
            response.put("data", clienteRequestDTO);
            return ResponseEntity.ok(response);
        } catch (ClienteNotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al obtener el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean createdCliente = clienteService.save(clienteRequestDTO);
            response.put("data", createdCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("error", "Error al crear el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean updated = clienteService.update(id, clienteRequestDTO);
            if (updated) {
                response.put("message", "Cliente actualizado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "No se pudo actualizar el cliente");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCliente(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
            response.put("message", "Cliente eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (ClienteNotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al eliminar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
