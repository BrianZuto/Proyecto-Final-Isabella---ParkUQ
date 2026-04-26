package co.edu.uniquindio.parkuq.model;

/**
 * Vehículo de tipo motocicleta. Isabella implementa calcularTarifa.
 *
 * @author Equipo PARKUQ
 */
public class Motocicleta extends Vehiculo {

    public Motocicleta(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, nombreConductor, identificacionConductor);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Isabella] – implementar la lógica real de tarifa para Motocicleta.
     */
    @Override
    public double calcularTarifa(long horas, Tarifa tarifa) {
        double base = Math.max(horas, 1) * tarifa.getValorPorHora();
        return base * (1 - tarifa.getDescuento());
    }
}
