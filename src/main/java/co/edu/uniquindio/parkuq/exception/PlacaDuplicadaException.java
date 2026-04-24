package co.edu.uniquindio.parkuq.exception;

/**
 * Se lanza cuando se intenta registrar un vehículo con una placa ya activa en el sistema.
 *
 * @author Equipo PARKUQ
 */
public class PlacaDuplicadaException extends Exception {

    public PlacaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
