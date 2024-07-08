package com.example.cuentas.validator;

import com.example.cuentas.dto.CuentaDTO;
import com.example.cuentas.exception.ApiUnprocessableEntity;

public interface ICuentaValidator {

    void validador(CuentaDTO cuentaDTO) throws ApiUnprocessableEntity;

}
