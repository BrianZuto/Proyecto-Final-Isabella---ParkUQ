package co.edu.uniquindio.parkuq.exception;

/**
 * Se lanza cuando las credenciales de inicio de sesión son incorrectas.
 *
 * @author Equipo PARKUQ
 */
public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
