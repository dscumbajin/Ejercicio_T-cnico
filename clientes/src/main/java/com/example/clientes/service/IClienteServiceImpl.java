package com.example.clientes.service;

import com.example.clientes.dto.ClienteRequestDTO;
import com.example.clientes.dto.ClienteResponseDTO;
import com.example.clientes.entity.Cliente;

import java.util.List;

public interface IClienteServiceImpl {

    public boolean save(Cliente cliente);
    public boolean update(Long id, Cliente cliente);
    public boolean delete(Long id);
    public List<ClienteResponseDTO> clienteDTOs();
    public ClienteResponseDTO findById(Long id);
    public ClienteRequestDTO findByIdentificacion(String identificacion);
    public ClienteRequestDTO findByNombre(String nombre);
}
