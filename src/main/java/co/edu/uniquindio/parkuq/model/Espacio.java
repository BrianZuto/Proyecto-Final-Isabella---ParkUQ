package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;

/**
 * Representa un espacio físico dentro del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class Espacio {

    private String codigo;
    private TipoEspacio tipoEspacio;
    private EstadoEspacio estado;
    private Vehiculo vehiculoAsignado;

    public Espacio(String codigo, TipoEspacio tipoEspacio) {
        this.codigo = codigo;
        this.tipoEspacio = tipoEspacio;
        this.estado = EstadoEspacio.DISPONIBLE;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public TipoEspacio getTipoEspacio() { return tipoEspacio; }
    public void setTipoEspacio(TipoEspacio tipoEspacio) { this.tipoEspacio = tipoEspacio; }

    public EstadoEspacio getEstado() { return estado; }
    public void setEstado(EstadoEspacio estado) { this.estado = estado; }

    public Vehiculo getVehiculoAsignado() { return vehiculoAsignado; }
    public void setVehiculoAsignado(Vehiculo vehiculoAsignado) { this.vehiculoAsignado = vehiculoAsignado; }
}
