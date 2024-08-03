package com.example.cuentas.controller;

import com.example.cuentas.dto.MovimientoDTO;
import com.example.cuentas.exception.MovimientoNotFoundException;
import com.example.cuentas.mapper.MovimientoMappers;
import com.example.cuentas.service.IMoviminetoServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movimientos")
public class MovimientoController {

    @Autowired
    private IMoviminetoServiceImpl movimientoService;

    @Autowired
    private MovimientoMappers movimientoMappers;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoService.movimientoDtos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO>getMovimientoById(@PathVariable Long id) {
        MovimientoDTO movimientoDTO = movimientoService.findById(id);
        return new ResponseEntity<>(movimientoDTO, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        try {
            return new ResponseEntity<>( movimientoMappers.toMovimientoDTO(movimientoService.save(movimientoDTO)),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovimiento(@PathVariable Long id, @Valid @RequestBody MovimientoDTO movimientoDTO) {
        try {
            return new ResponseEntity<>(movimientoMappers.toMovimientoDTO(movimientoService.update(id, movimientoDTO)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.delete(id);
            return new ResponseEntity<>("Movimiento eliminado correctamente", HttpStatus.OK);
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cuenta")
    public ResponseEntity<?> getMovimientoByNumberCuenta(@RequestParam String numero) {
        try {
        List<MovimientoDTO> movimientosDTO = movimientoService.findByCuentaNumero(numero);
        return new ResponseEntity<>(movimientosDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
