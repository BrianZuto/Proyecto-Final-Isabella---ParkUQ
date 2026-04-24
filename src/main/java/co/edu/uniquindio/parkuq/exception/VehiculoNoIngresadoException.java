package co.edu.uniquindio.parkuq.exception;

/**
 * Se lanza cuando se intenta registrar la salida de un vehículo que no figura como ingresado.
 *
 * @author Equipo PARKUQ
 */
public class VehiculoNoIngresadoException extends Exception {

    public VehiculoNoIngresadoException(String mensaje) {
        super(mensaje);
    }
}
