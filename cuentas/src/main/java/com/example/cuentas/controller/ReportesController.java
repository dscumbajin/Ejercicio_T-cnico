package com.example.cuentas.controller;

import com.example.cuentas.dto.ReporteDTO;
import com.example.cuentas.service.CuentaServiceImpl;
import com.example.cuentas.service.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reportes")
public class ReportesController {

    @Autowired
    private MovimientoServiceImpl movimientoService;

    @Autowired
    private CuentaServiceImpl cuentaService;

    @GetMapping("/cliente")
    public ResponseEntity<?> findByCuentaNumeroAndFechaBetween(@RequestParam String numero,
                                                               @RequestParam String fechaInicio,
                                                               @RequestParam String fechaFin) {
        try {
            List<ReporteDTO> reporteDTOS = movimientoService.findByCuentaNumeroAndFechaBetween(numero, fechaInicio, fechaFin);
            return new ResponseEntity<>(reporteDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<?> findByNombreAndFechaBetween(@RequestParam String nombre,
                                                               @RequestParam String fechaInicio,
                                                               @RequestParam String fechaFin) {
        try {
            List<ReporteDTO> reporteDTOS = movimientoService.findByNombreAndFechaBetween(nombre, fechaInicio, fechaFin);
            return new ResponseEntity<>(reporteDTOS.isEmpty() ? "No existen movimientos del cliente "+ nombre + " en el rango de fechas: " + fechaInicio + " - " + fechaFin : reporteDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
