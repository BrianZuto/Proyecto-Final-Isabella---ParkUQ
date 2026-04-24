package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;

/**
 * Usuario de tipo Estudiante de la Universidad del Quindío.
 *
 * @author Equipo PARKUQ
 */
public class Estudiante extends Usuario {

    public Estudiante(String nombre, String identificacion) {
        super(nombre, identificacion, TipoUsuario.ESTUDIANTE);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Juan David] – definir el descuento real para estudiantes.
     */
    @Override
    public double obtenerDescuento() {
        // TODO [Juan David]: retornar el descuento configurado para estudiantes
        return 0.0;
    }
}
