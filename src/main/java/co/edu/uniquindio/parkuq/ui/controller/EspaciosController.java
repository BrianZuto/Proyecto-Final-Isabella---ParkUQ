package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.model.Espacio;
import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de gestión de espacios del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class EspaciosController {

    @FXML private TableView<Espacio>            tablaEspacios;
    @FXML private TableColumn<Espacio, String>  colCodigo;
    @FXML private TableColumn<Espacio, String>  colTipo;
    @FXML private TableColumn<Espacio, String>  colEstado;
    @FXML private TableColumn<Espacio, String>  colVehiculo;

    @FXML private TextField              txtCodigo;
    @FXML private ComboBox<TipoEspacio>  cmbTipo;
    @FXML private Label                  lblMensaje;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getTipoEspacio().name()));
        colEstado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getEstado().name()));
        colVehiculo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getVehiculoAsignado() != null
                        ? c.getValue().getVehiculoAsignado().getPlaca() : "—"));

        cmbTipo.getItems().setAll(TipoEspacio.values());
        cmbTipo.getSelectionModel().selectFirst();
        refrescarTabla();
    }

    @FXML
    private void onAgregar() {
        lblMensaje.setText("");
        String codigo = txtCodigo.getText().trim().toUpperCase();
        if (codigo.isEmpty()) {
            lblMensaje.setText("Ingrese el código del espacio.");
            return;
        }
        Espacio nuevo = new Espacio(codigo, cmbTipo.getValue());
        Parqueadero.getInstancia().getEspaciosService().registrarEspacio(nuevo);
        lblMensaje.setStyle("-fx-text-fill: #27ae60;");
        lblMensaje.setText("Espacio " + codigo + " agregado.");
        txtCodigo.clear();
        refrescarTabla();
    }

    @FXML
    private void onDeshabilitar() {
        lblMensaje.setText("");
        Espacio sel = tablaEspacios.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMensaje.setText("Seleccione un espacio."); return; }
        if (sel.getEstado() == EstadoEspacio.OCUPADO) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("No puede deshabilitar un espacio ocupado.");
            return;
        }
        Parqueadero.getInstancia().getEspaciosService().deshabilitarEspacio(sel.getCodigo());
        lblMensaje.setStyle("-fx-text-fill: #e67e22;");
        lblMensaje.setText("Espacio " + sel.getCodigo() + " deshabilitado.");
        refrescarTabla();
    }

    @FXML
    private void onHabilitar() {
        lblMensaje.setText("");
        Espacio sel = tablaEspacios.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMensaje.setText("Seleccione un espacio."); return; }
        Parqueadero.getInstancia().getEspaciosService().habilitarEspacio(sel.getCodigo());
        lblMensaje.setStyle("-fx-text-fill: #27ae60;");
        lblMensaje.setText("Espacio " + sel.getCodigo() + " habilitado.");
        refrescarTabla();
    }

    @FXML
    private void onRefrescar() { refrescarTabla(); }

    private void refrescarTabla() {
        tablaEspacios.setItems(FXCollections.observableArrayList(
                Parqueadero.getInstancia().getEspaciosService().listarEspacios()));
    }
}
