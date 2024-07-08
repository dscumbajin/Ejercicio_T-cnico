package com.example.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    public String fecha;
    public String cliente;
    public String numeroCuenta;
    public String tipo;
    public double saldoInicial;
    public boolean estado;
    public String movimiento;
    public double saldoDisponible;

}
