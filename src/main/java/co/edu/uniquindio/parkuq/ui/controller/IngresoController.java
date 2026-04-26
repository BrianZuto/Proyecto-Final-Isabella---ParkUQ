package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.exception.PlacaDuplicadaException;
import co.edu.uniquindio.parkuq.model.*;
import co.edu.uniquindio.parkuq.model.enums.TipoVehiculo;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controlador de la pantalla de registro de ingreso de vehículos.
 *
 * @author Equipo PARKUQ
 */
public class IngresoController {

    @FXML private ComboBox<TipoVehiculo> cmbTipo;
    @FXML private TextField              txtPlaca;
    @FXML private TextField              txtConductor;
    @FXML private TextField              txtIdentificacion;
    @FXML private Label                  lblMensaje;
    @FXML private Label                  lblEspacio;

    @FXML
    private void initialize() {
        cmbTipo.getItems().setAll(TipoVehiculo.values());
        cmbTipo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onRegistrarIngreso() {
        lblMensaje.setText("");
        lblEspacio.setText("");

        String placa  = txtPlaca.getText().trim().toUpperCase();
        String nombre = txtConductor.getText().trim();
        String id     = txtIdentificacion.getText().trim();

        if (placa.isEmpty() || nombre.isEmpty() || id.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }
        if (cmbTipo.getValue() == null) {
            lblMensaje.setText("Seleccione el tipo de vehículo.");
            return;
        }

        Vehiculo vehiculo = crearVehiculo(cmbTipo.getValue(), placa, nombre, id);

        try {
            Parqueadero.getInstancia().getRegistroVehiculosService().registrarIngreso(vehiculo);
            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText("Ingreso registrado exitosamente.");
            lblEspacio.setText("Espacio asignado: " + vehiculo.getEspacioAsignado().getCodigo());
            limpiarFormulario();
        } catch (PlacaDuplicadaException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Placa duplicada: " + e.getMessage());
        } catch (EspacioNoDisponibleException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Sin espacio: " + e.getMessage());
        }
    }

    private Vehiculo crearVehiculo(TipoVehiculo tipo, String placa, String nombre, String id) {
        return switch (tipo) {
            case CARRO       -> new Carro(placa, nombre, id);
            case MOTOCICLETA -> new Motocicleta(placa, nombre, id);
            case BICICLETA   -> new Bicicleta(placa, nombre, id);
        };
    }

    private void limpiarFormulario() {
        txtPlaca.clear();
        txtConductor.clear();
        txtIdentificacion.clear();
        cmbTipo.getSelectionModel().selectFirst();
    }
}
