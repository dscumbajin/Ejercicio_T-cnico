package com.example.cuentas.service;

import com.example.cuentas.dto.CuentaDTO;
import com.example.cuentas.dto.CuentaRequestDTO;
import com.example.cuentas.entity.Cuenta;

import java.util.List;

public interface ICunetaServiceImpl {

    public boolean save(CuentaRequestDTO cuentaDTO);
    public boolean update(Long id, CuentaDTO cuentaDTO);
    public boolean delete(Long id);
    public List<CuentaDTO> cuentaDtos();
    public CuentaDTO findById(Long id);
}
