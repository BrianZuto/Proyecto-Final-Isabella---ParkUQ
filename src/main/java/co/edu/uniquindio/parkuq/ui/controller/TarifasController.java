package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.Tarifa;
import co.edu.uniquindio.parkuq.model.enums.TipoVehiculo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de gestión de tarifas (solo administrador).
 *
 * @author Equipo PARKUQ
 */
public class TarifasController {

    @FXML private TableView<Tarifa>            tablaTarifas;
    @FXML private TableColumn<Tarifa, String>  colTipo;
    @FXML private TableColumn<Tarifa, Double>  colValor;
    @FXML private TableColumn<Tarifa, Double>  colDescuento;

    @FXML private ComboBox<TipoVehiculo> cmbTipo;
    @FXML private TextField              txtValor;
    @FXML private TextField              txtDescuento;
    @FXML private Label                  lblMensaje;

    @FXML
    private void initialize() {
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getTipoVehiculo().name()));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPorHora"));
        colDescuento.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(
                c.getValue().getDescuento() * 100));

        cmbTipo.getItems().setAll(TipoVehiculo.values());
        cmbTipo.getSelectionModel().selectFirst();
        refrescarTabla();

        // Al seleccionar fila, cargar datos en el formulario
        tablaTarifas.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                cmbTipo.setValue(sel.getTipoVehiculo());
                txtValor.setText(String.valueOf(sel.getValorPorHora()));
                txtDescuento.setText(String.valueOf((int)(sel.getDescuento() * 100)));
            }
        });
    }

    @FXML
    private void onActualizar() {
        lblMensaje.setText("");
        TipoVehiculo tipo = cmbTipo.getValue();
        String valorStr    = txtValor.getText().trim();
        String descuentoStr = txtDescuento.getText().trim();

        if (valorStr.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Ingrese el valor por hora.");
            return;
        }

        try {
            double valor     = Double.parseDouble(valorStr);
            double descuento = descuentoStr.isEmpty() ? 0 : Double.parseDouble(descuentoStr) / 100.0;

            if (descuento < 0 || descuento > 1) {
                lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
                lblMensaje.setText("El descuento debe estar entre 0 y 100.");
                return;
            }

            Parqueadero p = Parqueadero.getInstancia();
            Tarifa existente = p.getTarifas().stream()
                    .filter(t -> t.getTipoVehiculo() == tipo)
                    .findFirst().orElse(null);

            if (existente != null) {
                existente.setValorPorHora(valor);
                existente.setDescuento(descuento);
            } else {
                p.getTarifas().add(new Tarifa(tipo, valor, descuento));
            }

            lblMensaje.setStyle("-fx-text-fill: #27ae60;");
            lblMensaje.setText("Tarifa de " + tipo + " actualizada.");
            refrescarTabla();
        } catch (NumberFormatException e) {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c;");
            lblMensaje.setText("Ingrese valores numéricos válidos.");
        }
    }

    private void refrescarTabla() {
        tablaTarifas.setItems(FXCollections.observableArrayList(
                Parqueadero.getInstancia().getTarifas()));
    }
}
