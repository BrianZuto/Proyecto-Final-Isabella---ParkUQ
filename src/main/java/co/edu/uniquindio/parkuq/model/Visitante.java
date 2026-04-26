package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;

/**
 * Usuario de tipo Visitante externo a la Universidad del Quindío.
 *
 * @author Equipo PARKUQ
 */
public class Visitante extends Usuario {

    public Visitante(String nombre, String identificacion) {
        super(nombre, identificacion, TipoUsuario.VISITANTE);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Juan David] – los visitantes generalmente no tienen descuento; confirmar.
     */
    @Override
    public double obtenerDescuento() {
        return 0.0; // visitantes sin descuento
    }
}
