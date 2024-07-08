package com.example.cuentas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "cuentas")
@Table(name = "cuentas", uniqueConstraints = {@UniqueConstraint(columnNames = {"numero"})})
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuentaId")
    private Long cuentaId;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "tipo", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldoInicial", nullable = false)
    private double saldoInicial;

    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "clienteId", nullable = false)
    private String clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    List<Movimiento> movimientos;
}
