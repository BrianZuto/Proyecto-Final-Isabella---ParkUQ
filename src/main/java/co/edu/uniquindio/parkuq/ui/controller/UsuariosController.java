package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.model.*;
import co.edu.uniquindio.parkuq.model.enums.TipoUsuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de gestión de usuarios autorizados.
 *
 * @author Equipo PARKUQ
 */
public class UsuariosController {

    @FXML private TableView<Usuario>            tablaUsuarios;
    @FXML private TableColumn<Usuario, String>  colNombre;
    @FXML private TableColumn<Usuario, String>  colIdentificacion;
    @FXML private TableColumn<Usuario, String>  colTipo;
    @FXML private TableColumn<Usuario, String>  colDescuento;

    @FXML private TextField              txtNombre;
    @FXML private TextField              txtIdentificacion;
    @FXML private ComboBox<TipoUsuario>  cmbTipo;
    @FXML private Label                  lblMensaje;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getTipoUsuario().name()));
        colDescuento.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                String.format("%.0f%%", c.getValue().obtenerDescuento() * 100)));

        cmbTipo.getItems().setAll(TipoUsuario.values());
        cmbTipo.getSelectionModel().selectFirst();
        refrescarTabla();
    }

    @FXML
    private void onAgregar() {
        lblMensaje.setText("");
        String nombre = txtNombre.getText().trim();
        String id     = txtIdentificacion.getText().trim();

        if (nombre.isEmpty() || id.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Nombre e identificación son obligatorios.");
            return;
        }

        Usuario nuevo = crearUsuario(cmbTipo.getValue(), nombre, id);
        try {
            Parqueadero.getInstancia().getUsuariosService().registrarUsuario(nuevo);
            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText("Usuario " + nombre + " registrado.");
            txtNombre.clear();
            txtIdentificacion.clear();
            refrescarTabla();
        } catch (IllegalArgumentException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        lblMensaje.setText("");
        Usuario sel = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMensaje.setText("Seleccione un usuario."); return; }
        boolean ok = Parqueadero.getInstancia().getUsuariosService()
                .eliminarUsuario(sel.getIdentificacion());
        if (ok) {
            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText("Usuario eliminado.");
            refrescarTabla();
        }
    }

    private Usuario crearUsuario(TipoUsuario tipo, String nombre, String id) {
        return switch (tipo) {
            case ESTUDIANTE     -> new Estudiante(nombre, id);
            case DOCENTE        -> new Docente(nombre, id);
            case ADMINISTRATIVO -> new Administrativo(nombre, id);
            case VISITANTE      -> new Visitante(nombre, id);
        };
    }

    private void refrescarTabla() {
        tablaUsuarios.setItems(FXCollections.observableArrayList(
                Parqueadero.getInstancia().getUsuariosService().listarUsuarios()));
    }
}
