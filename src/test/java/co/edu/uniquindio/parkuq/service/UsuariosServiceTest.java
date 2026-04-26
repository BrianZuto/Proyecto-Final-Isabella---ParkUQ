package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UsuariosService.
 */
class UsuariosServiceTest {

    private List<Usuario> usuarios;
    private UsuariosService service;

    @BeforeEach
    void setUp() {
        usuarios = new ArrayList<>();
        service  = new UsuariosService(usuarios);
    }

    @Test
    void registrarUsuario_exitoso() {
        service.registrarUsuario(new Estudiante("Ana", "1001"));
        assertEquals(1, usuarios.size());
    }

    @Test
    void registrarUsuario_idDuplicada_lanzaExcepcion() {
        service.registrarUsuario(new Estudiante("Ana", "1001"));
        assertThrows(IllegalArgumentException.class,
                () -> service.registrarUsuario(new Docente("Carlos", "1001")));
    }

    @Test
    void buscarPorIdentificacion_encontrado() {
        service.registrarUsuario(new Docente("Carlos", "2002"));
        Usuario u = service.buscarPorIdentificacion("2002");
        assertNotNull(u);
        assertEquals("Carlos", u.getNombre());
    }

    @Test
    void buscarPorIdentificacion_noEncontrado_retornaNull() {
        assertNull(service.buscarPorIdentificacion("9999"));
    }

    @Test
    void listarUsuarios_retornaLista() {
        service.registrarUsuario(new Estudiante("Ana",    "1001"));
        service.registrarUsuario(new Docente("Carlos",    "2002"));
        service.registrarUsuario(new Visitante("Pedro",   "3003"));
        assertEquals(3, service.listarUsuarios().size());
    }

    @Test
    void eliminarUsuario_exitoso() {
        service.registrarUsuario(new Estudiante("Ana", "1001"));
        boolean ok = service.eliminarUsuario("1001");
        assertTrue(ok);
        assertEquals(0, usuarios.size());
    }

    @Test
    void eliminarUsuario_noExiste_retornaFalse() {
        assertFalse(service.eliminarUsuario("9999"));
    }

    @Test
    void buscarPorIdentificacion_insensitivoMayusculas() {
        service.registrarUsuario(new Administrativo("Laura", "ADM001"));
        assertNotNull(service.buscarPorIdentificacion("adm001"));
    }
}
