package com.example.clientes.service;

import com.example.clientes.dto.ClienteRequestDTO;
import com.example.clientes.dto.ClienteResponseDTO;
import com.example.clientes.entity.Cliente;
import com.example.clientes.exception.ClienteNotFoundException;
import com.example.clientes.exception.ClienteYaExisteException;
import com.example.clientes.mapper.ClienteMapper;
import com.example.clientes.repository.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ClienteServiceImpl implements IClienteServiceImpl{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean save(Cliente cliente) {
        Cliente clientReq = clienteRepository.findByIdentificacion(cliente.getIdentificacion());
        if (clientReq != null) {
            throw new ClienteYaExisteException("La identificación debe ser única");
        } else {
            clienteRepository.save(cliente);
            return true;
        }
    }

    @Override
    public boolean update(Long id, Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        } else {
            Cliente cliente = clienteOptional.get();
                cliente.setNombre(clienteDetails.getNombre());
                cliente.setGenero(clienteDetails.getGenero());
                cliente.setEdad(clienteDetails.getEdad());
                cliente.setDireccion(clienteDetails.getDireccion());
                cliente.setTelefono(clienteDetails.getTelefono());
                cliente.setContrasena(clienteDetails.getContrasena());
                cliente.setEstado(clienteDetails.isEstado());
                clienteRepository.save(cliente);
            }
        return true;
    }

    @Override
    public boolean delete(Long id) {

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        } else {
            clienteRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<ClienteResponseDTO> clienteDTOs() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteMapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        return ClienteMapper.toClienteDTO(cliente);
    }

    @Override
    public ClienteRequestDTO findByIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado con identificacion: " + identificacion);
        } else {
            return ClienteMapper.toClienteIdDTO(cliente);
        }
    }

    @Override
    public ClienteRequestDTO findByNombre(String nombre) {
        Cliente cliente = clienteRepository.findByNombre(nombre);

        if (cliente == null) {
            throw new ClienteNotFoundException("No existe el cliente: " + nombre);
        } else {
            return ClienteMapper.toClienteIdDTO(cliente);
        }
    }
}

