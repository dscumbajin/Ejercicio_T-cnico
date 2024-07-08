package com.example.clientes.validator;

import com.example.clientes.entity.Cliente;
import com.example.clientes.exception.ApiUnprocessableEntity;

public interface IClienteValidator {

    void validador(Cliente cliente) throws ApiUnprocessableEntity;
}
