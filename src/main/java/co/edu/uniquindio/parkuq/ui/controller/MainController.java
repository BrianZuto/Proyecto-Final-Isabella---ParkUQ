package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.App;
import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.enums.Rol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador del menú principal con navegación lateral.
 *
 * @author Equipo PARKUQ
 */
public class MainController {

    @FXML private BorderPane root;
    @FXML private Label      lblUsuario;
    @FXML private Label      lblRol;
    @FXML private Button     btnUsuarios;
    @FXML private Button     btnTarifas;

    private Rol rolActual;

    /**
     * Llamado desde LoginController justo después de cargar el FXML.
     */
    public void inicializar(Rol rol) {
        this.rolActual = rol;
        String usuario = Parqueadero.getInstancia().getAutenticacionService().getUsuarioActual();
        lblUsuario.setText(usuario != null ? usuario : "—");
        lblRol.setText(rol.name());

        // Ocultar opciones de administrador para operadores
        if (rol == Rol.OPERADOR) {
            btnUsuarios.setVisible(false);
            btnTarifas.setVisible(false);
        }

        cargarVista("fxml/reportes.fxml"); // pantalla inicial: reportes / dashboard
    }

    @FXML private void onIngreso()   { cargarVista("fxml/ingreso.fxml"); }
    @FXML private void onSalida()    { cargarVista("fxml/salida.fxml"); }
    @FXML private void onEspacios()  { cargarVista("fxml/espacios.fxml"); }
    @FXML private void onUsuarios()  { cargarVista("fxml/usuarios.fxml"); }
    @FXML private void onTarifas()   { cargarVista("fxml/tarifas.fxml"); }
    @FXML private void onReportes()  { cargarVista("fxml/reportes.fxml"); }

    @FXML
    private void onCerrarSesion() {
        Parqueadero.getInstancia().getAutenticacionService().cerrarSesion();
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(App.class.getResource("styles/style.css").toExternalForm());
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Node nodo = loader.load();
            root.setCenter(nodo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
