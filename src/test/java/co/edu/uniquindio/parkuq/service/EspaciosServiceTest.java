package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.model.Espacio;
import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para EspaciosService.
 */
class EspaciosServiceTest {

    private List<Espacio> espacios;
    private EspaciosService service;

    @BeforeEach
    void setUp() {
        espacios = new ArrayList<>();
        espacios.add(new Espacio("C-01", TipoEspacio.CARRO));
        espacios.add(new Espacio("C-02", TipoEspacio.CARRO));
        espacios.add(new Espacio("M-01", TipoEspacio.MOTOCICLETA));
        espacios.add(new Espacio("B-01", TipoEspacio.BICICLETA));
        service = new EspaciosService(espacios);
    }

    @Test
    void buscarEspacioDisponible_carro_exitoso() throws Exception {
        Espacio e = service.buscarEspacioDisponible(TipoEspacio.CARRO);
        assertNotNull(e);
        assertEquals(TipoEspacio.CARRO, e.getTipoEspacio());
    }

    @Test
    void buscarEspacioDisponible_sinEspacios_lanzaExcepcion() {
        espacios.forEach(e -> e.setEstado(EstadoEspacio.OCUPADO));
        assertThrows(EspacioNoDisponibleException.class,
                () -> service.buscarEspacioDisponible(TipoEspacio.CARRO));
    }

    @Test
    void buscarEspacioDisponible_ignoraFueraDeServicio() throws Exception {
        // Deshabilitar C-01
        espacios.get(0).setEstado(EstadoEspacio.FUERA_DE_SERVICIO);
        Espacio e = service.buscarEspacioDisponible(TipoEspacio.CARRO);
        assertEquals("C-02", e.getCodigo());
    }

    @Test
    void listarEspacios_retornaTodos() {
        assertEquals(4, service.listarEspacios().size());
    }

    @Test
    void registrarEspacio_aumentaLista() {
        service.registrarEspacio(new Espacio("C-03", TipoEspacio.CARRO));
        assertEquals(5, service.listarEspacios().size());
    }

    @Test
    void deshabilitarEspacio_cambiaEstado() {
        service.deshabilitarEspacio("C-01");
        assertEquals(EstadoEspacio.FUERA_DE_SERVICIO, espacios.get(0).getEstado());
    }

    @Test
    void habilitarEspacio_desdeFueraDeServicio() {
        service.deshabilitarEspacio("C-01");
        service.habilitarEspacio("C-01");
        assertEquals(EstadoEspacio.DISPONIBLE, espacios.get(0).getEstado());
    }

    @Test
    void contarDisponiblesPorTipo_carro() {
        assertEquals(2, service.contarDisponiblesPorTipo(TipoEspacio.CARRO));
    }

    @Test
    void contarDisponiblesPorTipo_disminuyeCuandoOcupado() {
        espacios.get(0).setEstado(EstadoEspacio.OCUPADO);
        assertEquals(1, service.contarDisponiblesPorTipo(TipoEspacio.CARRO));
    }
}
