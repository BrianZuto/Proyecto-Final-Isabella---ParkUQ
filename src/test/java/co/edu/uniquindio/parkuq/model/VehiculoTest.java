package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoVehiculo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Vehiculo y sus subclases.
 */
class VehiculoTest {

    private Tarifa tarifaCarro()       { return new Tarifa(TipoVehiculo.CARRO,       3000, 0); }
    private Tarifa tarifaMoto()        { return new Tarifa(TipoVehiculo.MOTOCICLETA, 2000, 0); }
    private Tarifa tarifaBicicleta()   { return new Tarifa(TipoVehiculo.BICICLETA,    500, 0); }
    private Tarifa tarifaConDescuento(){ return new Tarifa(TipoVehiculo.CARRO,       3000, 0.20); }

    // ---- Carro ----

    @Test
    void carro_calcularTarifa_2horas() {
        Carro c = new Carro("ABC123", "Juan", "1001");
        assertEquals(6000, c.calcularTarifa(2, tarifaCarro()), 0.01);
    }

    @Test
    void carro_calcularTarifa_minimoUnaHora() {
        Carro c = new Carro("ABC123", "Juan", "1001");
        assertEquals(3000, c.calcularTarifa(0, tarifaCarro()), 0.01);
    }

    @Test
    void carro_calcularTarifa_conDescuento20pct() {
        Carro c = new Carro("ABC123", "Juan", "1001");
        // 3000 * (1 - 0.20) = 2400
        assertEquals(2400, c.calcularTarifa(1, tarifaConDescuento()), 0.01);
    }

    // ---- Motocicleta ----

    @Test
    void motocicleta_calcularTarifa_3horas() {
        Motocicleta m = new Motocicleta("MOT001", "Pedro", "2001");
        assertEquals(6000, m.calcularTarifa(3, tarifaMoto()), 0.01);
    }

    @Test
    void motocicleta_calcularTarifa_cero_horas_minimoUna() {
        Motocicleta m = new Motocicleta("MOT001", "Pedro", "2001");
        assertEquals(2000, m.calcularTarifa(0, tarifaMoto()), 0.01);
    }

    // ---- Bicicleta ----

    @Test
    void bicicleta_calcularTarifa_5horas() {
        Bicicleta b = new Bicicleta("BIC001", "Laura", "3001");
        assertEquals(2500, b.calcularTarifa(5, tarifaBicicleta()), 0.01);
    }

    // ---- Estado inicial ----

    @Test
    void vehiculo_estadoInicialEsDentro() {
        Carro c = new Carro("XYZ", "Ana", "999");
        assertEquals(co.edu.uniquindio.parkuq.model.enums.EstadoVehiculo.DENTRO, c.getEstado());
    }

    @Test
    void vehiculo_gettersCorrectos() {
        Carro c = new Carro("KLM456", "Carlos", "555");
        assertEquals("KLM456", c.getPlaca());
        assertEquals("Carlos", c.getNombreConductor());
        assertEquals("555",    c.getIdentificacionConductor());
    }
}
