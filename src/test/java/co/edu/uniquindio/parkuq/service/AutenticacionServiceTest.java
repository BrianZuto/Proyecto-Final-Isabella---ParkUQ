package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.CredencialesInvalidasException;
import co.edu.uniquindio.parkuq.model.enums.Rol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para AutenticacionService.
 */
class AutenticacionServiceTest {

    private AutenticacionService service;

    @BeforeEach
    void setUp() {
        service = new AutenticacionService();
    }

    @Test
    void autenticar_adminCorrecto_retornaAdministrador() throws Exception {
        Rol rol = service.autenticar("admin", "admin123");
        assertEquals(Rol.ADMINISTRADOR, rol);
    }

    @Test
    void autenticar_operadorCorrecto_retornaOperador() throws Exception {
        Rol rol = service.autenticar("operador", "op123");
        assertEquals(Rol.OPERADOR, rol);
    }

    @Test
    void autenticar_passwordIncorrecta_lanzaExcepcion() {
        assertThrows(CredencialesInvalidasException.class,
                () -> service.autenticar("admin", "wrongpass"));
    }

    @Test
    void autenticar_usuarioInexistente_lanzaExcepcion() {
        assertThrows(CredencialesInvalidasException.class,
                () -> service.autenticar("noexiste", "pass123"));
    }

    @Test
    void autenticar_camposVacios_lanzaExcepcion() {
        assertThrows(CredencialesInvalidasException.class,
                () -> service.autenticar("", ""));
    }

    @Test
    void autenticar_guardarUsuarioActual() throws Exception {
        service.autenticar("admin", "admin123");
        assertEquals("admin", service.getUsuarioActual());
    }

    @Test
    void cerrarSesion_limpia_usuarioActual() throws Exception {
        service.autenticar("admin", "admin123");
        service.cerrarSesion();
        assertNull(service.getUsuarioActual());
    }

    @Test
    void registrarOperador_nuevoUsuario_puedeAutenticar() throws Exception {
        service.registrarOperador("nuevo", "clave123", Rol.OPERADOR);
        Rol rol = service.autenticar("nuevo", "clave123");
        assertEquals(Rol.OPERADOR, rol);
    }

    @Test
    void autenticar_insensitivo_mayusculas() throws Exception {
        Rol rol = service.autenticar("ADMIN", "admin123");
        assertEquals(Rol.ADMINISTRADOR, rol);
    }
}
