package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;

/**
 * Usuario de tipo Administrativo de la Universidad del Quindío.
 *
 * @author Equipo PARKUQ
 */
public class Administrativo extends Usuario {

    public Administrativo(String nombre, String identificacion) {
        super(nombre, identificacion, TipoUsuario.ADMINISTRATIVO);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Juan David] – definir el descuento real para administrativos.
     */
    @Override
    public double obtenerDescuento() {
        return 0.15; // 15% descuento para personal administrativo
    }
}
