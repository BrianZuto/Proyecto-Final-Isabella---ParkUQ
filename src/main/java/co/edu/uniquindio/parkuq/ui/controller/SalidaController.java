package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.exception.VehiculoNoIngresadoException;
import co.edu.uniquindio.parkuq.model.Parqueadero;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Controlador de la pantalla de registro de salida de vehículos.
 *
 * @author Equipo PARKUQ
 */
public class SalidaController {

    @FXML private TextField txtPlaca;
    @FXML private Label     lblMensaje;
    @FXML private Label     lblCobro;

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    @FXML
    private void onRegistrarSalida() {
        lblMensaje.setText("");
        lblCobro.setText("");

        String placa = txtPlaca.getText().trim().toUpperCase();
        if (placa.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Ingrese la placa del vehículo.");
            return;
        }

        try {
            double cobro = Parqueadero.getInstancia()
                    .getRegistroVehiculosService()
                    .registrarSalida(placa);

            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText("Salida registrada para: " + placa);
            lblCobro.setText("Total a cobrar: " + FMT.format(cobro));
            txtPlaca.clear();
        } catch (VehiculoNoIngresadoException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText(e.getMessage());
        }
    }
}
