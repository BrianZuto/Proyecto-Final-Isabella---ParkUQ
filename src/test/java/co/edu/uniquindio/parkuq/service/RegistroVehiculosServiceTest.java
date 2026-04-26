package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.exception.PlacaDuplicadaException;
import co.edu.uniquindio.parkuq.exception.VehiculoNoIngresadoException;
import co.edu.uniquindio.parkuq.model.*;
import co.edu.uniquindio.parkuq.model.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para RegistroVehiculosService.
 */
class RegistroVehiculosServiceTest {

    private List<Vehiculo> vehiculos;
    private List<Espacio>  espacios;
    private List<Tarifa>   tarifas;
    private List<Usuario>  usuarios;
    private EspaciosService espaciosService;
    private ReportesService reportesService;
    private RegistroVehiculosService service;

    @BeforeEach
    void setUp() {
        vehiculos = new ArrayList<>();
        espacios  = new ArrayList<>();
        tarifas   = new ArrayList<>();
        usuarios  = new ArrayList<>();

        espacios.add(new Espacio("C-01", TipoEspacio.CARRO));
        espacios.add(new Espacio("M-01", TipoEspacio.MOTOCICLETA));
        espacios.add(new Espacio("B-01", TipoEspacio.BICICLETA));

        tarifas.add(new Tarifa(TipoVehiculo.CARRO,       3000, 0));
        tarifas.add(new Tarifa(TipoVehiculo.MOTOCICLETA, 2000, 0));
        tarifas.add(new Tarifa(TipoVehiculo.BICICLETA,    500, 0));

        espaciosService  = new EspaciosService(espacios);
        reportesService  = new ReportesService(vehiculos, espacios);
        service = new RegistroVehiculosService(vehiculos, tarifas, usuarios, espaciosService, reportesService);
    }

    // ---- registrarIngreso ----

    @Test
    void registrarIngreso_exitoso_carro() throws Exception {
        Carro c = new Carro("ABC123", "Juan", "1001");
        service.registrarIngreso(c);
        assertEquals(1, vehiculos.size());
        assertEquals(EstadoVehiculo.DENTRO, c.getEstado());
        assertNotNull(c.getEspacioAsignado());
        assertNotNull(c.getHoraIngreso());
    }

    @Test
    void registrarIngreso_placaDuplicada_lanzaExcepcion() throws Exception {
        Carro c1 = new Carro("DUP001", "Juan", "1001");
        service.registrarIngreso(c1);
        Carro c2 = new Carro("DUP001", "Pedro", "2002");
        assertThrows(PlacaDuplicadaException.class, () -> service.registrarIngreso(c2));
    }

    @Test
    void registrarIngreso_sinEspacioDisponible_lanzaExcepcion() throws Exception {
        Carro c1 = new Carro("CAR001", "Ana", "111");
        service.registrarIngreso(c1);
        // Ya no hay más espacios CARRO
        Carro c2 = new Carro("CAR002", "Luis", "222");
        assertThrows(EspacioNoDisponibleException.class, () -> service.registrarIngreso(c2));
    }

    @Test
    void registrarIngreso_espacioMarcadoOcupado() throws Exception {
        Carro c = new Carro("ABC123", "Juan", "1001");
        service.registrarIngreso(c);
        assertEquals(EstadoEspacio.OCUPADO, c.getEspacioAsignado().getEstado());
    }

    // ---- registrarSalida ----

    @Test
    void registrarSalida_exitoso() throws Exception {
        Carro c = new Carro("SAL001", "María", "3003");
        service.registrarIngreso(c);
        double cobro = service.registrarSalida("SAL001");
        assertTrue(cobro >= 0);
        assertEquals(EstadoVehiculo.SALIO, c.getEstado());
        assertEquals(EstadoEspacio.DISPONIBLE, c.getEspacioAsignado().getEstado());
    }

    @Test
    void registrarSalida_vehiculoNoExiste_lanzaExcepcion() {
        assertThrows(VehiculoNoIngresadoException.class,
                () -> service.registrarSalida("NOEXISTE"));
    }

    @Test
    void registrarSalida_conDescuentoEstudiante() throws Exception {
        usuarios.add(new Estudiante("Ana García", "EST001"));
        Carro c = new Carro("EST_CAR", "Ana García", "EST001");
        service.registrarIngreso(c);
        double cobro = service.registrarSalida("EST_CAR");
        // Con 20% descuento, cobro < tarifa normal mínima
        assertTrue(cobro < 3000);
    }

    // ---- buscarPorPlaca ----

    @Test
    void buscarPorPlaca_encontrado() throws Exception {
        Carro c = new Carro("BUS001", "Test", "9999");
        service.registrarIngreso(c);
        assertNotNull(service.buscarPorPlaca("BUS001"));
    }

    @Test
    void buscarPorPlaca_noEncontrado_retornaNull() {
        assertNull(service.buscarPorPlaca("XXXXX"));
    }

    @Test
    void placaDuplicada_permiteReingresoDespuesDeSalida() throws Exception {
        Carro c = new Carro("REI001", "Luis", "111");
        service.registrarIngreso(c);
        service.registrarSalida("REI001");
        // Ahora puede volver a ingresar
        Carro c2 = new Carro("REI001", "Luis", "111");
        assertDoesNotThrow(() -> service.registrarIngreso(c2));
    }
}
