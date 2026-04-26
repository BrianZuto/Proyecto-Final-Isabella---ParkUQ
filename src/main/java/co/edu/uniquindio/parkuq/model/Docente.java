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
        return 0.30; // 30% descuento para docentes
    }
}
