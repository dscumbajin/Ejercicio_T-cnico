package com.example.cuentas.service;

import com.example.cuentas.client.ClienteClient;
import com.example.cuentas.dto.ClienteDTO;
import com.example.cuentas.dto.CuentaDTO;
import com.example.cuentas.dto.CuentaRequestDTO;
import com.example.cuentas.entity.Cuenta;
import com.example.cuentas.exception.CuentaNotFoundException;
import com.example.cuentas.exception.CuentaYaExisteException;
import com.example.cuentas.mapper.CuentaMappers;
import com.example.cuentas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements ICunetaServiceImpl {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private CuentaMappers cuentaMappers;

    @Override
    public boolean save(CuentaRequestDTO cuentaRequestDTO) {
        Cuenta cuentaReq = cuentaRepository.findByNumero(cuentaRequestDTO.getNumero());
        if (cuentaReq != null) {
            throw new CuentaYaExisteException("El número de cuenta debe ser único");
        } else {
            if(cuentaRequestDTO.getIdentificacion() != null){
                ClienteDTO clienteDTO = clienteClient.getCliente(cuentaRequestDTO.getIdentificacion());
                if (clienteDTO != null) {
                    Cuenta cuenta = cuentaMappers.toCuenta(cuentaRequestDTO);
                    cuenta.setClienteId(clienteDTO.getId().toString());
                    cuentaRepository.save(cuenta);
                    return true;
                } else {
                    throw new CuentaNotFoundException("Cliente no encontrado");
                }
            }else{
                throw new CuentaNotFoundException("Identificacion es requerida");
            }
        }
    }

    @Override
    public boolean update(Long id, CuentaDTO cuentaDTO) {
        Optional<Cuenta> clienteOptional = cuentaRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new CuentaNotFoundException("No existe la cuenta con el ID:  " + id);
        } else {
            Cuenta cuenta = clienteOptional.get();
            cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
            cuenta.setEstado(cuentaDTO.isEstado());
            cuentaRepository.save(cuenta);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Cuenta> clienteOptional = cuentaRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new CuentaNotFoundException("Cuenta no encontrada con ID: " + id);
        } else {
            cuentaRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<CuentaDTO> cuentaDtos() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        List<Cuenta> cuentasDetalles = new ArrayList<>();
        if(!cuentas.isEmpty()){
            for (Cuenta cuenta : cuentas) {
                ClienteDTO cliente = clienteClient.getClienteById(Long.parseLong(cuenta.getClienteId()));
                cuenta.setClienteId(cliente.getNombre());
                cuentasDetalles.add(cuenta);
            }
        }
        return cuentasDetalles.stream()
                .map(cuentaMappers::toCuentaDTO)
                .toList();
    }

    @Override
    public CuentaDTO findById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrado con ID: " + id));
        ClienteDTO clienteDTO = clienteClient.getClienteById(Long.parseLong(cuenta.getClienteId()));
        cuenta.setClienteId(clienteDTO.getNombre());
        return cuentaMappers.toCuentaDTO(cuenta);
    }
}
