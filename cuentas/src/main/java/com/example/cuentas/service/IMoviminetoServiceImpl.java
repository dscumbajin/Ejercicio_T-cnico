package com.example.cuentas.service;

import com.example.cuentas.dto.MovimientoDTO;
import com.example.cuentas.dto.ReporteDTO;
import com.example.cuentas.entity.Movimiento;

import java.util.List;

public interface IMoviminetoServiceImpl {

    public Movimiento save(MovimientoDTO movimientoDTO);
    public Movimiento update(Long id, MovimientoDTO movimientoDTO);
    public boolean delete(Long id);
    public List<MovimientoDTO> movimientoDtos();
    public MovimientoDTO findById(Long id);
    public List<MovimientoDTO> findByCuentaNumero(String numero);
    public List<ReporteDTO> findByCuentaNumeroAndFechaBetween(String numero, String fechaInicio, String fechaFin);
    public List<ReporteDTO> findByNombreAndFechaBetween(String numero, String fechaInicio, String fechaFin);
}
