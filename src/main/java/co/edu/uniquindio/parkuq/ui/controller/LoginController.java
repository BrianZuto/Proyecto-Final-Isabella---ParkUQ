package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.App;
import co.edu.uniquindio.parkuq.exception.CredencialesInvalidasException;
import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.enums.Rol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la pantalla de inicio de sesión.
 *
 * @author Equipo PARKUQ
 */
public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label         lblError;
    @FXML private Button        btnIngresar;

    @FXML
    private void onIngresarClick() {
        String usuario    = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();
        lblError.setText("");

        try {
            Rol rol = Parqueadero.getInstancia()
                    .getAutenticacionService()
                    .autenticar(usuario, contrasena);
            navegarAMain(rol);
        } catch (CredencialesInvalidasException e) {
            lblError.setText(e.getMessage());
        }
    }

    private void navegarAMain(Rol rol) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/main.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 700);
            scene.getStylesheets().add(App.class.getResource("styles/style.css").toExternalForm());

            MainController controller = loader.getController();
            controller.inicializar(rol);

            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.setTitle("PARKUQ – Sistema de Parqueadero UniQuindío");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            lblError.setText("Error al cargar la pantalla principal.");
            e.printStackTrace();
        }
    }
}
