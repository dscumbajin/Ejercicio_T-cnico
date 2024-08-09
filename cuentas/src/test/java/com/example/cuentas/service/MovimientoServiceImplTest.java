package com.example.cuentas.service;

import com.example.cuentas.client.ClienteClient;
import com.example.cuentas.dto.ClienteDTO;
import com.example.cuentas.dto.MovimientoDTO;
import com.example.cuentas.dto.ReporteDTO;
import com.example.cuentas.entity.Cuenta;
import com.example.cuentas.entity.Movimiento;
import com.example.cuentas.exception.CuentaNotFoundException;
import com.example.cuentas.exception.MovimientoNotFoundException;
import com.example.cuentas.mapper.MovimientoMappers;
import com.example.cuentas.repository.CuentaRepository;
import com.example.cuentas.repository.MovimientoRepository;
import com.example.cuentas.util.Conversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private MovimientoMappers movimientoMappers;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private ClienteClient clienteClient;
    String nombre;
    String fechaInicio;
    String fechaFin;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nombre = "Juan Pérez";
        fechaInicio = "01/08/2024";
        fechaFin = "09/08/2024";
    }

    @Test
    void testSaveMovimiento() {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumero("12345");
        movimientoDTO.setTipo("Deposito");
        movimientoDTO.setValor("100.00");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumero("12345");
        cuenta.setSaldoInicial(500.00);

        when(cuentaRepository.findByNumero("12345")).thenReturn(cuenta);
        when(movimientoMappers.toMovimiento(movimientoDTO)).thenReturn(new Movimiento());

        Movimiento result = movimientoService.save(movimientoDTO);

        assertEquals(600.00, result.getSaldo());
        verify(movimientoRepository, times(1)).save(any(Movimiento.class));
    }

    @Test
    void testSaveMovimientoCuentaNoEncontrada() {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setNumero("99999");

        when(cuentaRepository.findByNumero("99999")).thenReturn(null);

        assertThrows(CuentaNotFoundException.class, () -> {
            movimientoService.save(movimientoDTO);
        });
    }

    @Test
    void testUpdateMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        movimiento.setSaldo(500.00);

        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setTipo("Retiro");
        movimientoDTO.setValor("100.00");

        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimiento));

        Movimiento updatedMovimiento = movimientoService.update(1L, movimientoDTO);

        assertEquals(400.00, updatedMovimiento.getSaldo());
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    void testUpdateMovimientoNoEncontrado() {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setTipo("Deposito");
        movimientoDTO.setValor("100.00");

        when(movimientoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovimientoNotFoundException.class, () -> {
            movimientoService.update(1L, movimientoDTO);
        });
    }

    @Test
    void testDeleteMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);

        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimiento));

        boolean result = movimientoService.delete(1L);

        assertEquals(true, result);
        verify(movimientoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMovimientoNoEncontrado() {
        when(movimientoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovimientoNotFoundException.class, () -> {
            movimientoService.delete(1L);
        });
    }

    @Test
    void testMovimientoDtos() {
        Movimiento movimiento1 = new Movimiento();
        Movimiento movimiento2 = new Movimiento();
        List<Movimiento> movimientos = Arrays.asList(movimiento1, movimiento2);
        MovimientoDTO movimientoDTO1 = new MovimientoDTO();
        MovimientoDTO movimientoDTO2 = new MovimientoDTO();
        when(movimientoRepository.findAll()).thenReturn(movimientos);
        when(movimientoMappers.toMovimientoDTO(movimiento1)).thenReturn(movimientoDTO1);
        when(movimientoMappers.toMovimientoDTO(movimiento2)).thenReturn(movimientoDTO2);
        List<MovimientoDTO> result = movimientoService.movimientoDtos();
        assertEquals(2, result.size());
        verify(movimientoMappers, times(1)).toMovimientoDTO(movimiento1);
        verify(movimientoMappers, times(1)).toMovimientoDTO(movimiento2);
        verify(movimientoRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdMovimientoExistente() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimiento));
        when(movimientoMappers.toMovimientoDTO(movimiento)).thenReturn(movimientoDTO);
        MovimientoDTO result = movimientoService.findById(1L);
        assertEquals(movimientoDTO, result);
        verify(movimientoRepository, times(1)).findById(1L);
        verify(movimientoMappers, times(1)).toMovimientoDTO(movimiento);
    }

    @Test
    void testFindByIdMovimientoNoEncontrado() {
        when(movimientoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MovimientoNotFoundException.class, () -> {
            movimientoService.findById(1L);
        });
        verify(movimientoRepository, times(1)).findById(1L);
        verify(movimientoMappers, never()).toMovimientoDTO(any());
    }

    @Test
    void testFindByCuentaNumeroConMovimientos() {
        Movimiento movimiento1 = new Movimiento();
        Movimiento movimiento2 = new Movimiento();
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(movimiento1);
        movimientos.add(movimiento2);
        MovimientoDTO movimientoDTO1 = new MovimientoDTO();
        MovimientoDTO movimientoDTO2 = new MovimientoDTO();
        when(movimientoRepository.findByCuentaNumero("12345")).thenReturn(movimientos);
        when(movimientoMappers.toMovimientoDTO(movimiento1)).thenReturn(movimientoDTO1);
        when(movimientoMappers.toMovimientoDTO(movimiento2)).thenReturn(movimientoDTO2);
        List<MovimientoDTO> result = movimientoService.findByCuentaNumero("12345");
        assertEquals(2, result.size());
        assertEquals(movimientoDTO1, result.get(0));
        assertEquals(movimientoDTO2, result.get(1));
        verify(movimientoRepository, times(1)).findByCuentaNumero("12345");
        verify(movimientoMappers, times(1)).toMovimientoDTO(movimiento1);
        verify(movimientoMappers, times(1)).toMovimientoDTO(movimiento2);
    }

    @Test
    void testFindByCuentaNumeroSinMovimientos() {
        when(movimientoRepository.findByCuentaNumero("12345")).thenReturn(new ArrayList<>());
        assertThrows(MovimientoNotFoundException.class, () -> {
            movimientoService.findByCuentaNumero("12345");
        });
        verify(movimientoRepository, times(1)).findByCuentaNumero("12345");
        verify(movimientoMappers, never()).toMovimientoDTO(any());
    }

    @Test
    void testFindByCuentaNumeroAndFechaBetweenConMovimientos() {
        String numeroCuenta = "12345";
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);

        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId("1");
        cuenta.setNumero(numeroCuenta);

        Movimiento movimiento1 = new Movimiento();
        movimiento1.setCuenta(cuenta);
        Movimiento movimiento2 = new Movimiento();
        movimiento2.setCuenta(cuenta);

        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(movimiento1);
        movimientos.add(movimiento2);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan Pérez");

        ReporteDTO reporteDTO1 = new ReporteDTO();
        ReporteDTO reporteDTO2 = new ReporteDTO();

        when(movimientoRepository.findByCuentaNumeroAndFechaBetween(numeroCuenta, inicioFecha, finFecha)).thenReturn(movimientos);
        when(clienteClient.getClienteById(anyLong())).thenReturn(clienteDTO);
        when(movimientoMappers.toReporteDTO(movimiento1)).thenReturn(reporteDTO1);
        when(movimientoMappers.toReporteDTO(movimiento2)).thenReturn(reporteDTO2);

        List<ReporteDTO> result = movimientoService.findByCuentaNumeroAndFechaBetween(numeroCuenta, fechaInicio, fechaFin);

        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).getCliente());
        assertEquals("Juan Pérez", result.get(1).getCliente());

        verify(movimientoRepository, times(1)).findByCuentaNumeroAndFechaBetween(numeroCuenta, inicioFecha, finFecha);
        verify(clienteClient, times(2)).getClienteById(anyLong());
        verify(movimientoMappers, times(2)).toReporteDTO(any(Movimiento.class));
    }

    @Test
    void testFindByCuentaNumeroAndFechaBetweenSinMovimientos() {

        String numeroCuenta = "12345";
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);

        when(movimientoRepository.findByCuentaNumeroAndFechaBetween(numeroCuenta, inicioFecha, finFecha)).thenReturn(new ArrayList<>());

        assertThrows(MovimientoNotFoundException.class, () -> {
            movimientoService.findByCuentaNumeroAndFechaBetween(numeroCuenta, fechaInicio, fechaFin);
        });

        verify(movimientoRepository, times(1)).findByCuentaNumeroAndFechaBetween(numeroCuenta, inicioFecha, finFecha);
        verify(clienteClient, never()).getClienteById(anyLong());
        verify(movimientoMappers, never()).toReporteDTO(any());
    }

    @Test
    void testFindByNombreAndFechaBetweenConMovimientos() {
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre(nombre);

        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId("1");
        cuenta.setNumero("12345");

        Movimiento movimiento1 = new Movimiento();
        movimiento1.setCuenta(cuenta);
        movimiento1.setTipo("Retiro");
        movimiento1.setValor("-100");

        Movimiento movimiento2 = new Movimiento();
        movimiento2.setCuenta(cuenta);
        movimiento2.setTipo("Depósito");
        movimiento2.setValor("200");

        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(cuenta);

        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(movimiento1);
        movimientos.add(movimiento2);

        ReporteDTO reporteDTO1 = new ReporteDTO();
        reporteDTO1.setCliente(nombre);
        reporteDTO1.setMovimiento("-100");

        ReporteDTO reporteDTO2 = new ReporteDTO();
        reporteDTO2.setCliente(nombre);
        reporteDTO2.setMovimiento("200");

        when(clienteClient.getClienteByName(nombre)).thenReturn(clienteDTO);
        when(cuentaRepository.findByClienteId(clienteDTO.getId().toString())).thenReturn(cuentas);
        when(movimientoRepository.findByCuentaNumeroAndFechaBetween(cuenta.getNumero(), inicioFecha, finFecha)).thenReturn(movimientos);
        when(clienteClient.getClienteById(anyLong())).thenReturn(clienteDTO);
        when(movimientoMappers.toReporteDTO(movimiento1)).thenReturn(reporteDTO1);
        when(movimientoMappers.toReporteDTO(movimiento2)).thenReturn(reporteDTO2);

        List<ReporteDTO> result = movimientoService.findByNombreAndFechaBetween(nombre, fechaInicio, fechaFin);

        assertEquals(2, result.size());
        assertEquals("-100", result.get(0).getMovimiento());
        assertEquals("200", result.get(1).getMovimiento());

        verify(clienteClient, times(1)).getClienteByName(nombre);
        verify(cuentaRepository, times(1)).findByClienteId(clienteDTO.getId().toString());
        verify(movimientoRepository, times(1)).findByCuentaNumeroAndFechaBetween(cuenta.getNumero(), inicioFecha, finFecha);
        verify(movimientoMappers, times(2)).toReporteDTO(any(Movimiento.class));
    }

    @Test
    void testFindByNombreAndFechaBetweenSinMovimientos() {
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre(nombre);

        List<Cuenta> cuentas = new ArrayList<>();
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero("12345");
        cuentas.add(cuenta);

        when(clienteClient.getClienteByName(nombre)).thenReturn(clienteDTO);
        when(cuentaRepository.findByClienteId(clienteDTO.getId().toString())).thenReturn(cuentas);
        when(movimientoRepository.findByCuentaNumeroAndFechaBetween(cuenta.getNumero(), inicioFecha, finFecha)).thenReturn(new ArrayList<>());

        List<ReporteDTO> result = movimientoService.findByNombreAndFechaBetween(nombre, fechaInicio, fechaFin);

        assertTrue(result.isEmpty());

        verify(clienteClient, times(1)).getClienteByName(nombre);
        verify(cuentaRepository, times(1)).findByClienteId(clienteDTO.getId().toString());
        verify(movimientoRepository, times(1)).findByCuentaNumeroAndFechaBetween(cuenta.getNumero(), inicioFecha, finFecha);
        verify(movimientoMappers, never()).toReporteDTO(any(Movimiento.class));
    }

    @Test
    void testFindByNombreAndFechaBetweenClienteNoEncontrado() {
        when(clienteClient.getClienteByName(nombre)).thenReturn(null);

        List<ReporteDTO> result = movimientoService.findByNombreAndFechaBetween(nombre, fechaInicio, fechaFin);

        assertTrue(result.isEmpty());

        verify(clienteClient, times(1)).getClienteByName(nombre);
        verify(cuentaRepository, never()).findByClienteId(anyString());
        verify(movimientoRepository, never()).findByCuentaNumeroAndFechaBetween(anyString(), any(Date.class), any(Date.class));
        verify(movimientoMappers, never()).toReporteDTO(any(Movimiento.class));
    }

}