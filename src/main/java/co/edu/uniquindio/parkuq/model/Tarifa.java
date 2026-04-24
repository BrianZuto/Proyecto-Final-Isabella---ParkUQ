package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoVehiculo;

/**
 * Representa la tarifa cobrada por hora para un tipo de vehículo específico.
 *
 * @author Equipo PARKUQ
 */
public class Tarifa {

    private TipoVehiculo tipoVehiculo;
    private double valorPorHora;
    private double descuento;

    public Tarifa(TipoVehiculo tipoVehiculo, double valorPorHora, double descuento) {
        this.tipoVehiculo = tipoVehiculo;
        this.valorPorHora = valorPorHora;
        this.descuento = descuento;
    }

    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public double getValorPorHora() { return valorPorHora; }
    public void setValorPorHora(double valorPorHora) { this.valorPorHora = valorPorHora; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
}
