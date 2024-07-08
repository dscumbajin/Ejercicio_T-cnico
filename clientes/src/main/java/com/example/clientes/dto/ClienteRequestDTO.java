package com.example.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;
}
