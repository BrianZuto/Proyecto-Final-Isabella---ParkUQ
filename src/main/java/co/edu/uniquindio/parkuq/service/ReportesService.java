package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.Espacio;
import co.edu.uniquindio.parkuq.model.Vehiculo;
import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.EstadoVehiculo;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;

import java.util.List;

/**
 * Servicio para la generación de reportes del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class ReportesService {

    private final List<Vehiculo> vehiculos;
    private final List<Espacio> espacios;
    private double ingresosDiarios;

    public ReportesService(List<Vehiculo> vehiculos, List<Espacio> espacios) {
        this.vehiculos = vehiculos;
        this.espacios = espacios;
        this.ingresosDiarios = 0;
    }

    /** Lista los vehículos que se encuentran actualmente DENTRO del parqueadero. */
    public List<Vehiculo> listarVehiculosDentro() {
        return vehiculos.stream()
                .filter(v -> v.getEstado() == EstadoVehiculo.DENTRO)
                .toList();
    }

    /** Retorna los ingresos acumulados del día. */
    public double calcularIngresosDiarios() {
        return ingresosDiarios;
    }

    /** Acumula un cobro al total de ingresos diarios. */
    public void registrarIngreso(double monto) {
        ingresosDiarios += monto;
    }

    /** Reinicia el contador de ingresos diarios (inicio de jornada). */
    public void reiniciarIngresosDiarios() {
        ingresosDiarios = 0;
    }

    /** Genera un resumen de ocupación por tipo de espacio. */
    public String generarResumenOcupacion() {
        long carrosTotal       = contarPorTipo(TipoEspacio.CARRO);
        long carrosOcupados    = contarOcupadosPorTipo(TipoEspacio.CARRO);
        long motosTotal        = contarPorTipo(TipoEspacio.MOTOCICLETA);
        long motosOcupadas     = contarOcupadosPorTipo(TipoEspacio.MOTOCICLETA);
        long bicisTotal        = contarPorTipo(TipoEspacio.BICICLETA);
        long bicisOcupadas     = contarOcupadosPorTipo(TipoEspacio.BICICLETA);

        return String.format(
                "CARROS: %d/%d ocupados%n" +
                "MOTOS:  %d/%d ocupadas%n" +
                "BICIS:  %d/%d ocupadas",
                carrosOcupados, carrosTotal,
                motosOcupadas, motosTotal,
                bicisOcupadas, bicisTotal);
    }

    private long contarPorTipo(TipoEspacio tipo) {
        return espacios.stream().filter(e -> e.getTipoEspacio() == tipo).count();
    }

    private long contarOcupadosPorTipo(TipoEspacio tipo) {
        return espacios.stream()
                .filter(e -> e.getTipoEspacio() == tipo && e.getEstado() == EstadoEspacio.OCUPADO)
                .count();
    }

    /** Cuenta total de vehículos DENTRO. */
    public long contarVehiculosDentro() {
        return vehiculos.stream().filter(v -> v.getEstado() == EstadoVehiculo.DENTRO).count();
    }
}
