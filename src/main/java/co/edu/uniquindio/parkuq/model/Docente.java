package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;

/**
 * Usuario de tipo Docente de la Universidad del Quindío.
 *
 * @author Equipo PARKUQ
 */
public class Docente extends Usuario {

    public Docente(String nombre, String identificacion) {
        super(nombre, identificacion, TipoUsuario.DOCENTE);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Juan David] – definir el descuento real para docentes.
     */
    @Override
    public double obtenerDescuento() {
        // TODO [Juan David]: retornar el descuento configurado para docentes
        return 0.0;
    }
}
