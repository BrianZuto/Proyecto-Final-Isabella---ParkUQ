package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.CredencialesInvalidasException;
import co.edu.uniquindio.parkuq.model.enums.Rol;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de autenticación y gestión de sesión del operador/administrador.
 *
 * @author Equipo PARKUQ
 */
public class AutenticacionService {

    /** Mapa usuario → contraseña. */
    private final Map<String, String> credenciales = new HashMap<>();

    /** Mapa usuario → rol en el sistema. */
    private final Map<String, Rol> roles = new HashMap<>();

    /** Usuario actualmente autenticado (null si no hay sesión). */
    private String usuarioActual;

    public AutenticacionService() {
        // Credenciales predeterminadas
        credenciales.put("admin", "admin123");
        roles.put("admin", Rol.ADMINISTRADOR);

        credenciales.put("operador", "op123");
        roles.put("operador", Rol.OPERADOR);
    }

    /**
     * Autentica con usuario y contraseña; retorna el rol asignado.
     *
     * @throws CredencialesInvalidasException si el usuario no existe o la contraseña es incorrecta
     */
    public Rol autenticar(String usuario, String contrasena) throws CredencialesInvalidasException {
        if (usuario == null || usuario.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new CredencialesInvalidasException("Usuario y contraseña son obligatorios.");
        }
        String passGuardada = credenciales.get(usuario.trim().toLowerCase());
        if (passGuardada == null || !passGuardada.equals(contrasena)) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
        usuarioActual = usuario.trim().toLowerCase();
        return roles.get(usuarioActual);
    }

    /** Cierra la sesión actual. */
    public void cerrarSesion() {
        usuarioActual = null;
    }

    /** Retorna el nombre del usuario actualmente autenticado. */
    public String getUsuarioActual() {
        return usuarioActual;
    }

    /** Agrega o actualiza un operador/administrador en el sistema. */
    public void registrarOperador(String usuario, String contrasena, Rol rol) {
        credenciales.put(usuario.trim().toLowerCase(), contrasena);
        roles.put(usuario.trim().toLowerCase(), rol);
    }
}
