package co.edu.uniquindio.parkuq.exception;

/**
 * Se lanza cuando no hay espacios disponibles para el tipo de vehículo solicitado.
 *
 * @author Equipo PARKUQ
 */
public class EspacioNoDisponibleException extends Exception {

    public EspacioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
