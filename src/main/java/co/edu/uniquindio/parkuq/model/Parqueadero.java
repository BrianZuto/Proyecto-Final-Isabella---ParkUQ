package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase fachada principal del sistema PARKUQ.
 * Centraliza el acceso a todas las entidades y servicios del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class Parqueadero {

    private static Parqueadero instancia;

    private final List<Vehiculo> vehiculos;
    private final List<Espacio> espacios;
    private final List<Usuario> usuarios;
    private final List<Tarifa> tarifas;

    // Referencias a servicios
    private RegistroVehiculosService registroVehiculosService;
    private EspaciosService espaciosService;
    private UsuariosService usuariosService;
    private AutenticacionService autenticacionService;
    private ReportesService reportesService;

    private Parqueadero() {
        this.vehiculos  = new ArrayList<>();
        this.espacios   = new ArrayList<>();
        this.usuarios   = new ArrayList<>();
        this.tarifas    = new ArrayList<>();

        // TODO [Reinel]: inicializar los servicios aquí cuando estén implementados
    }

    /** Implementación del patrón Singleton para el parqueadero. */
    public static Parqueadero getInstancia() {
        if (instancia == null) {
            instancia = new Parqueadero();
        }
        return instancia;
    }

    // ---- Getters ----

    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public List<Espacio>  getEspacios()  { return espacios; }
    public List<Usuario>  getUsuarios()  { return usuarios; }
    public List<Tarifa>   getTarifas()   { return tarifas; }

    public RegistroVehiculosService getRegistroVehiculosService() { return registroVehiculosService; }
    public EspaciosService          getEspaciosService()          { return espaciosService; }
    public UsuariosService          getUsuariosService()          { return usuariosService; }
    public AutenticacionService     getAutenticacionService()     { return autenticacionService; }
    public ReportesService          getReportesService()          { return reportesService; }
}
