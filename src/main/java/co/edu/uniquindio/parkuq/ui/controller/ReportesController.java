package co.edu.uniquindio.parkuq.ui.controller;

import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.Vehiculo;
import co.edu.uniquindio.parkuq.service.ReportesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Controlador de la pantalla de reportes y dashboard.
 *
 * @author Equipo PARKUQ
 */
public class ReportesController {

    @FXML private Label lblVehiculosDentro;
    @FXML private Label lblIngresosDiarios;
    @FXML private Label lblOcupacion;

    @FXML private TableView<Vehiculo>           tablaVehiculos;
    @FXML private TableColumn<Vehiculo, String> colPlaca;
    @FXML private TableColumn<Vehiculo, String> colTipo;
    @FXML private TableColumn<Vehiculo, String> colConductor;
    @FXML private TableColumn<Vehiculo, String> colIngreso;
    @FXML private TableColumn<Vehiculo, String> colEspacio;

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    @FXML
    private void initialize() {
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getClass().getSimpleName()));
        colConductor.setCellValueFactory(new PropertyValueFactory<>("nombreConductor"));
        colIngreso.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getHoraIngreso() != null
                        ? c.getValue().getHoraIngreso().toString().replace("T", " ").substring(0, 16)
                        : "—"));
        colEspacio.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEspacioAsignado() != null
                        ? c.getValue().getEspacioAsignado().getCodigo() : "—"));

        refrescar();
    }

    @FXML
    private void onRefrescar() { refrescar(); }

    @FXML
    private void onReiniciarIngresos() {
        Parqueadero.getInstancia().getReportesService().reiniciarIngresosDiarios();
        refrescar();
    }

    private void refrescar() {
        ReportesService srv = Parqueadero.getInstancia().getReportesService();
        lblVehiculosDentro.setText(String.valueOf(srv.contarVehiculosDentro()));
        lblIngresosDiarios.setText(FMT.format(srv.calcularIngresosDiarios()));
        lblOcupacion.setText(srv.generarResumenOcupacion());
        tablaVehiculos.setItems(FXCollections.observableArrayList(srv.listarVehiculosDentro()));
    }
}
