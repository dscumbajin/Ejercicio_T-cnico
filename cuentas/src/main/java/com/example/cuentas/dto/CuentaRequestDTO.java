package com.example.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {
    private String numero;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private String identificacion;
}
