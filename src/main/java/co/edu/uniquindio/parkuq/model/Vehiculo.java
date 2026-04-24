package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.EstadoVehiculo;

import java.time.LocalDateTime;

/**
 * Clase abstracta que representa un vehículo en el parqueadero.
 * Cada subtipo concreto implementa su propio cálculo de tarifa.
 *
 * @author Equipo PARKUQ
 */
public abstract class Vehiculo {

    protected String placa;
    protected String nombreConductor;
    protected String identificacionConductor;
    protected LocalDateTime horaIngreso;
    protected Espacio espacioAsignado;
    protected EstadoVehiculo estado;

    public Vehiculo(String placa, String nombreConductor, String identificacionConductor) {
        this.placa = placa;
        this.nombreConductor = nombreConductor;
        this.identificacionConductor = identificacionConductor;
        this.estado = EstadoVehiculo.DENTRO;
    }

    /**
     * Calcula el valor a cobrar según las horas transcurridas y la tarifa vigente.
     *
     * @param horas   horas de permanencia en el parqueadero
     * @param tarifa  tarifa aplicable al tipo de vehículo
     * @return valor total a cobrar
     */
    public abstract double calcularTarifa(long horas, Tarifa tarifa);

    // ---- Getters y Setters ----

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getNombreConductor() { return nombreConductor; }
    public void setNombreConductor(String nombreConductor) { this.nombreConductor = nombreConductor; }

    public String getIdentificacionConductor() { return identificacionConductor; }
    public void setIdentificacionConductor(String identificacionConductor) {
        this.identificacionConductor = identificacionConductor;
    }

    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalDateTime horaIngreso) { this.horaIngreso = horaIngreso; }

    public Espacio getEspacioAsignado() { return espacioAsignado; }
    public void setEspacioAsignado(Espacio espacioAsignado) { this.espacioAsignado = espacioAsignado; }

    public EstadoVehiculo getEstado() { return estado; }
    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }
}
