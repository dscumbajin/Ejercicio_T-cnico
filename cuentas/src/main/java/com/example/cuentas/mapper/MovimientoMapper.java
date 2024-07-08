package com.example.cuentas.mapper;

import com.example.cuentas.dto.MovimientoDTO;
import com.example.cuentas.dto.ReporteDTO;
import com.example.cuentas.entity.Movimiento;
import com.example.cuentas.util.Conversion;

public class MovimientoMapper {

    public static MovimientoDTO toMovimientoDTO(Movimiento movimiento) {
        return new MovimientoDTO(movimiento.getCuenta().getNumero(), movimiento.getCuenta().getTipoCuenta(),movimiento.getSaldo(),
                movimiento.getCuenta().isEstado(), movimiento.getTipo()+" de "+movimiento.getValor());
    }
    public static Movimiento toMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(movimientoDTO.getTipo());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setSaldo(movimientoDTO.getSaldo());
        return movimiento;
    }

    public static ReporteDTO toReporteDTO(Movimiento movimiento) {
        return new ReporteDTO(Conversion.convertDateToString(movimiento.getFecha()),movimiento.getCuenta().getClienteId(),movimiento.getCuenta().getNumero(),
                movimiento.getCuenta().getTipoCuenta(), movimiento.getCuenta().getSaldoInicial(),
                movimiento.getCuenta().isEstado(), movimiento.getValor(), movimiento.getSaldo());
    }
}
