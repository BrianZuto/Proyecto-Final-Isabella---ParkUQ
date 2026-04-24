package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.model.Vehiculo;

import java.util.List;

/**
 * Servicio para la generación de reportes del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class ReportesService {

    /**
     * Lista los vehículos que se encuentran actualmente DENTRO del parqueadero.
     *
     * TODO [Reinel] – implementar.
     */
    public List<Vehiculo> listarVehiculosDentro() {
        // TODO [Reinel]: implementar
        return List.of();
    }

    /**
     * Calcula los ingresos totales del día actual.
     *
     * TODO [Reinel] – implementar.
     *
     * @return total de ingresos en pesos colombianos
     */
    public double calcularIngresosDiarios() {
        // TODO [Reinel]: implementar
        return 0;
    }

    /**
     * Genera un resumen de ocupación actual de espacios por tipo.
     *
     * TODO [Reinel] – implementar: retornar mapa TipoEspacio → cantidad ocupada.
     */
    public String generarResumenOcupacion() {
        // TODO [Reinel]: implementar
        return "Reporte no implementado aún.";
    }
}
