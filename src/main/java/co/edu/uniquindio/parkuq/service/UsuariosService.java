package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.Usuario;

import java.util.List;

/**
 * Servicio de gestión de usuarios del sistema.
 *
 * @author Equipo PARKUQ
 */
public class UsuariosService {

    private final List<Usuario> usuarios;

    public UsuariosService(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Registra un nuevo usuario validando que la identificación no esté duplicada.
     *
     * @throws IllegalArgumentException si ya existe un usuario con esa identificación
     */
    public void registrarUsuario(Usuario usuario) {
        if (buscarPorIdentificacion(usuario.getIdentificacion()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con identificación: " + usuario.getIdentificacion());
        }
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario por número de identificación.
     *
     * @return usuario encontrado o null si no existe
     */
    public Usuario buscarPorIdentificacion(String identificacion) {
        return usuarios.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(identificacion))
                .findFirst()
                .orElse(null);
    }

    /** Lista todos los usuarios registrados. */
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    /**
     * Elimina un usuario del sistema por identificación.
     *
     * @return true si fue eliminado, false si no existía
     */
    public boolean eliminarUsuario(String identificacion) {
        return usuarios.removeIf(u -> u.getIdentificacion().equalsIgnoreCase(identificacion));
    }
}
