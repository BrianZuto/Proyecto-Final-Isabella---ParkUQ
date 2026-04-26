package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Espacio.
 */
class EspacioTest {

    @Test
    void espacio_estadoInicialDisponible() {
        Espacio e = new Espacio("C-01", TipoEspacio.CARRO);
        assertEquals(EstadoEspacio.DISPONIBLE, e.getEstado());
    }

    @Test
    void espacio_sinVehiculoAsignadoInicial() {
        Espacio e = new Espacio("M-01", TipoEspacio.MOTOCICLETA);
        assertNull(e.getVehiculoAsignado());
    }

    @Test
    void espacio_cambiarEstadoAOcupado() {
        Espacio e = new Espacio("C-02", TipoEspacio.CARRO);
        e.setEstado(EstadoEspacio.OCUPADO);
        assertEquals(EstadoEspacio.OCUPADO, e.getEstado());
    }

    @Test
    void espacio_cambiarEstadoFueraDeServicio() {
        Espacio e = new Espacio("B-01", TipoEspacio.BICICLETA);
        e.setEstado(EstadoEspacio.FUERA_DE_SERVICIO);
        assertEquals(EstadoEspacio.FUERA_DE_SERVICIO, e.getEstado());
    }

    @Test
    void espacio_asignarVehiculo() {
        Espacio e = new Espacio("C-03", TipoEspacio.CARRO);
        Carro c = new Carro("AAA111", "Test", "000");
        e.setVehiculoAsignado(c);
        assertNotNull(e.getVehiculoAsignado());
        assertEquals("AAA111", e.getVehiculoAsignado().getPlaca());
    }

    @Test
    void espacio_gettersCorrectos() {
        Espacio e = new Espacio("M-03", TipoEspacio.MOTOCICLETA);
        assertEquals("M-03",              e.getCodigo());
        assertEquals(TipoEspacio.MOTOCICLETA, e.getTipoEspacio());
    }
}
