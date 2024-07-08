package com.example.cuentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private String numero;
    private String tipo;
    private double saldo;
    private boolean estado;
    private String valor;
}
