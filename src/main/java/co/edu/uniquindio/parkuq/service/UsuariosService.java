package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.Usuario;

import java.util.List;

/**
 * Servicio de gestión de usuarios del sistema.
 *
 * @author Equipo PARKUQ
 */
public class UsuariosService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * TODO [Juan David] – implementar: validar que la identificación no esté duplicada.
     *
     * @param usuario usuario a registrar
     */
    public void registrarUsuario(Usuario usuario) {
        // TODO [Juan David]: implementar
    }

    /**
     * Busca un usuario por número de identificación.
     *
     * TODO [Juan David] – implementar.
     *
     * @param identificacion número de identificación
     * @return usuario encontrado o null
     */
    public Usuario buscarPorIdentificacion(String identificacion) {
        // TODO [Juan David]: implementar
        return null;
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * TODO [Juan David] – implementar.
     */
    public List<Usuario> listarUsuarios() {
        // TODO [Juan David]: implementar
        return List.of();
    }
}
