package co.edu.uniquindio.parkuq.model;

/**
 * Vehículo de tipo bicicleta. Isabella implementa calcularTarifa.
 *
 * @author Equipo PARKUQ
 */
public class Bicicleta extends Vehiculo {

    public Bicicleta(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, nombreConductor, identificacionConductor);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Isabella] – implementar la lógica real de tarifa para Bicicleta.
     */
    @Override
    public double calcularTarifa(long horas, Tarifa tarifa) {
        double base = Math.max(horas, 1) * tarifa.getValorPorHora();
        return base * (1 - tarifa.getDescuento());
    }
}
