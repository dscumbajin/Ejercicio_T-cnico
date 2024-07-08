package com.example.clientes.mapper;

import com.example.clientes.dto.ClienteRequestDTO;
import com.example.clientes.dto.ClienteResponseDTO;
import com.example.clientes.entity.Cliente;

public class ClienteMapper {
    public static ClienteResponseDTO toClienteDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getNombre(), cliente.getDireccion(),cliente.getTelefono(),cliente.getContrasena(),cliente.isEstado());
    }

    public static ClienteRequestDTO toClienteIdDTO(Cliente cliente) {
        return new ClienteRequestDTO(cliente.getId(),cliente.getNombre(), cliente.getDireccion(),cliente.getTelefono(),cliente.getContrasena(),cliente.isEstado());
    }


    public static Cliente toCliente(ClienteResponseDTO clienteResponseDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteResponseDTO.getNombre());
        cliente.setDireccion(clienteResponseDTO.getDireccion());
        cliente.setTelefono(clienteResponseDTO.getTelefono());
        cliente.setContrasena(clienteResponseDTO.getContrasena());
        return cliente;
    }


}
