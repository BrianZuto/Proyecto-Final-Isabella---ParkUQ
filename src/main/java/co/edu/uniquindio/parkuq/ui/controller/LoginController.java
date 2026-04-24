package co.edu.uniquindio.parkuq.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la vista de inicio de sesión (login.fxml).
 *
 * @author Equipo PARKUQ
 */
public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIngresar;

    /**
     * Acción ejecutada al hacer clic en el botón "Ingresar".
     *
     * TODO [Juan David] – implementar: llamar a AutenticacionService,
     *                     mostrar error si las credenciales son inválidas,
     *                     cargar la vista principal si son correctas.
     */
    @FXML
    private void onIngresarClick() {
        String usuario    = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        // TODO [Juan David]: validar con AutenticacionService y navegar a la siguiente vista
        System.out.println("Intento de login – usuario: " + usuario);
    }
}
