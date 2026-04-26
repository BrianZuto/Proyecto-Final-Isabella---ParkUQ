package co.edu.uniquindio.parkuq.model;

import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoVehiculo;
import co.edu.uniquindio.parkuq.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase fachada y Singleton del sistema PARKUQ.
 * Centraliza entidades y servicios, e inicializa los datos de partida.
 *
 * @author Equipo PARKUQ
 */
public class Parqueadero {

    private static Parqueadero instancia;

    private final List<Vehiculo> vehiculos;
    private final List<Espacio>  espacios;
    private final List<Usuario>  usuarios;
    private final List<Tarifa>   tarifas;

    private final RegistroVehiculosService registroVehiculosService;
    private final EspaciosService          espaciosService;
    private final UsuariosService          usuariosService;
    private final AutenticacionService     autenticacionService;
    private final ReportesService          reportesService;

    private Parqueadero() {
        vehiculos = new ArrayList<>();
        espacios  = new ArrayList<>();
        usuarios  = new ArrayList<>();
        tarifas   = new ArrayList<>();

        inicializarEspacios();
        inicializarTarifas();
        inicializarUsuariosDemostracion();

        // Construir servicios inyectando las colecciones compartidas
        autenticacionService  = new AutenticacionService();
        espaciosService       = new EspaciosService(espacios);
        reportesService       = new ReportesService(vehiculos, espacios);
        usuariosService       = new UsuariosService(usuarios);
        registroVehiculosService = new RegistroVehiculosService(
                vehiculos, tarifas, usuarios, espaciosService, reportesService);
    }

    /** Patrón Singleton. */
    public static Parqueadero getInstancia() {
        if (instancia == null) {
            instancia = new Parqueadero();
        }
        return instancia;
    }

    // ---- Inicialización de datos ----

    private void inicializarEspacios() {
        for (int i = 1; i <= 10; i++) {
            espacios.add(new Espacio(String.format("C-%02d", i), TipoEspacio.CARRO));
        }
        for (int i = 1; i <= 5; i++) {
            espacios.add(new Espacio(String.format("M-%02d", i), TipoEspacio.MOTOCICLETA));
        }
        for (int i = 1; i <= 5; i++) {
            espacios.add(new Espacio(String.format("B-%02d", i), TipoEspacio.BICICLETA));
        }
    }

    private void inicializarTarifas() {
        tarifas.add(new Tarifa(TipoVehiculo.CARRO,       3000, 0));
        tarifas.add(new Tarifa(TipoVehiculo.MOTOCICLETA, 2000, 0));
        tarifas.add(new Tarifa(TipoVehiculo.BICICLETA,    500, 0));
    }

    private void inicializarUsuariosDemostracion() {
        usuarios.add(new Estudiante("Ana García",        "1001234567"));
        usuarios.add(new Docente("Carlos Pérez",          "79123456"));
        usuarios.add(new Administrativo("Laura Gómez",   "43876543"));
        usuarios.add(new Visitante("Pedro Ramírez",       "9999999"));
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
