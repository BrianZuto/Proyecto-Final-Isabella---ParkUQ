package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Usuario y sus subclases.
 */
class UsuarioTest {

    @Test
    void estudiante_descuento20pct() {
        Estudiante e = new Estudiante("Ana", "1001");
        assertEquals(0.20, e.obtenerDescuento(), 0.001);
    }

    @Test
    void docente_descuento30pct() {
        Docente d = new Docente("Carlos", "2002");
        assertEquals(0.30, d.obtenerDescuento(), 0.001);
    }

    @Test
    void administrativo_descuento15pct() {
        Administrativo a = new Administrativo("Laura", "3003");
        assertEquals(0.15, a.obtenerDescuento(), 0.001);
    }

    @Test
    void visitante_sinDescuento() {
        Visitante v = new Visitante("Pedro", "4004");
        assertEquals(0.0, v.obtenerDescuento(), 0.001);
    }

    @Test
    void estudiante_tipoUsuarioCorrecto() {
        Estudiante e = new Estudiante("Ana", "1001");
        assertEquals(TipoUsuario.ESTUDIANTE, e.getTipoUsuario());
    }

    @Test
    void docente_tipoUsuarioCorrecto() {
        Docente d = new Docente("Carlos", "2002");
        assertEquals(TipoUsuario.DOCENTE, d.getTipoUsuario());
    }

    @Test
    void administrativo_tipoUsuarioCorrecto() {
        Administrativo a = new Administrativo("Laura", "3003");
        assertEquals(TipoUsuario.ADMINISTRATIVO, a.getTipoUsuario());
    }

    @Test
    void visitante_tipoUsuarioCorrecto() {
        Visitante v = new Visitante("Pedro", "4004");
        assertEquals(TipoUsuario.VISITANTE, v.getTipoUsuario());
    }

    @Test
    void usuario_gettersCorrectos() {
        Estudiante e = new Estudiante("María López", "9876");
        assertEquals("María López", e.getNombre());
        assertEquals("9876",        e.getIdentificacion());
    }
}
