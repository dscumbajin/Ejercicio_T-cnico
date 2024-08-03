package com.example.cuentas.controller;

import com.example.cuentas.dto.CuentaDTO;
import com.example.cuentas.dto.CuentaRequestDTO;
import com.example.cuentas.exception.CuentaNotFoundException;
import com.example.cuentas.service.CuentaServiceImpl;
import com.example.cuentas.service.ICunetaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    private ICunetaServiceImpl cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>>  getAllCuentas() {
        return ResponseEntity.ok(cuentaService.cuentaDtos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getCuentaById(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(cuentaService.findById(id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCuenta(@Valid @RequestBody CuentaRequestDTO cuentaDTO) {
        try {
            cuentaService.save(cuentaDTO);
            return new ResponseEntity<>("Se creo con exito la cuenta: " + cuentaDTO.getNumero(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        try {
            boolean resp = cuentaService.update(id, cuentaDTO);
            return new ResponseEntity<>("Cuenta actualizado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.delete(id);
            return new ResponseEntity<>("Cuenta eliminado correctamente", HttpStatus.OK);
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
