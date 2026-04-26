package co.edu.uniquindio.parkuq.model;

/**
 * Vehículo de tipo carro. Isabella implementa calcularTarifa.
 *
 * @author Equipo PARKUQ
 */
public class Carro extends Vehiculo {

    public Carro(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, nombreConductor, identificacionConductor);
    }

    /**
     * {@inheritDoc}
     *
     * TODO [Isabella] – implementar la lógica real de tarifa para Carro.
     */
    @Override
    public double calcularTarifa(long horas, Tarifa tarifa) {
        double base = Math.max(horas, 1) * tarifa.getValorPorHora();
        return base * (1 - tarifa.getDescuento());
    }
}
