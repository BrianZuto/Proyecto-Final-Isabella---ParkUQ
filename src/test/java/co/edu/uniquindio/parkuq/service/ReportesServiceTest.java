package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.*;
import co.edu.uniquindio.parkuq.model.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ReportesService.
 */
class ReportesServiceTest {

    private List<Vehiculo> vehiculos;
    private List<Espacio>  espacios;
    private ReportesService service;

    @BeforeEach
    void setUp() {
        vehiculos = new ArrayList<>();
        espacios  = new ArrayList<>();

        espacios.add(new Espacio("C-01", TipoEspacio.CARRO));
        espacios.add(new Espacio("C-02", TipoEspacio.CARRO));
        espacios.add(new Espacio("M-01", TipoEspacio.MOTOCICLETA));

        service = new ReportesService(vehiculos, espacios);
    }

    @Test
    void listarVehiculosDentro_inicialmenteVacio() {
        assertTrue(service.listarVehiculosDentro().isEmpty());
    }

    @Test
    void listarVehiculosDentro_soloMuestraDentro() {
        Carro c1 = new Carro("AAA", "A", "1");
        Carro c2 = new Carro("BBB", "B", "2");
        c2.setEstado(EstadoVehiculo.SALIO);
        vehiculos.add(c1);
        vehiculos.add(c2);
        assertEquals(1, service.listarVehiculosDentro().size());
    }

    @Test
    void calcularIngresosDiarios_inicialmenteCero() {
        assertEquals(0.0, service.calcularIngresosDiarios(), 0.01);
    }

    @Test
    void registrarIngreso_acumulaCorrectamente() {
        service.registrarIngreso(3000);
        service.registrarIngreso(2000);
        assertEquals(5000, service.calcularIngresosDiarios(), 0.01);
    }

    @Test
    void reiniciarIngresosDiarios_vuelveACero() {
        service.registrarIngreso(5000);
        service.reiniciarIngresosDiarios();
        assertEquals(0.0, service.calcularIngresosDiarios(), 0.01);
    }

    @Test
    void generarResumenOcupacion_formatoCorrecto() {
        String resumen = service.generarResumenOcupacion();
        assertTrue(resumen.contains("CARROS"));
        assertTrue(resumen.contains("MOTOS"));
        assertTrue(resumen.contains("BICIS"));
    }

    @Test
    void generarResumenOcupacion_cuentaOcupados() {
        espacios.get(0).setEstado(EstadoEspacio.OCUPADO);
        String resumen = service.generarResumenOcupacion();
        assertTrue(resumen.contains("1/2")); // 1 de 2 carros ocupados
    }

    @Test
    void contarVehiculosDentro_correcto() {
        Carro c = new Carro("X01", "X", "0");
        vehiculos.add(c);
        assertEquals(1, service.contarVehiculosDentro());
        c.setEstado(EstadoVehiculo.SALIO);
        assertEquals(0, service.contarVehiculosDentro());
    }
}
