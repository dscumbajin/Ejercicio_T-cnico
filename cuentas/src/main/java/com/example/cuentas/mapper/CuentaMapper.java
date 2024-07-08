package com.example.cuentas.mapper;

import com.example.cuentas.dto.CuentaDTO;
import com.example.cuentas.dto.CuentaRequestDTO;
import com.example.cuentas.entity.Cuenta;

public class CuentaMapper {

    public static CuentaDTO toCuentaDTO(Cuenta cuenta) {
        return new CuentaDTO(cuenta.getNumero(), cuenta.getTipoCuenta(), cuenta.getSaldoInicial(), cuenta.isEstado(), cuenta.getClienteId());
    }
    public static Cuenta toCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(cuentaDTO.getNumero());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.isEstado());
        return cuenta;
    }

    public static Cuenta toCuenta(CuentaRequestDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(cuentaDTO.getNumero());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.isEstado());
        return cuenta;
    }
}
