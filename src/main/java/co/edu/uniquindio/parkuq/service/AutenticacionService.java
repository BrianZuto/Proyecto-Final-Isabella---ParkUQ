package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.CredencialesInvalidasException;
import co.edu.uniquindio.parkuq.model.enums.Rol;

/**
 * Servicio de autenticación y gestión de sesión del operador/administrador.
 *
 * @author Equipo PARKUQ
 */
public class AutenticacionService {

    /**
     * Autentica a un usuario del sistema con usuario y contraseña.
     *
     * TODO [Juan David] – implementar: validar credenciales y retornar el Rol.
     *
     * @param usuario    nombre de usuario
     * @param contrasena contraseña
     * @return rol del usuario autenticado
     * @throws CredencialesInvalidasException si las credenciales son incorrectas
     */
    public Rol autenticar(String usuario, String contrasena)
            throws CredencialesInvalidasException {
        // TODO [Juan David]: implementar lógica de autenticación
        throw new CredencialesInvalidasException("Autenticación no implementada aún.");
    }

    /**
     * Cierra la sesión del usuario actual.
     *
     * TODO [Juan David] – implementar.
     */
    public void cerrarSesion() {
        // TODO [Juan David]: implementar
    }
}
