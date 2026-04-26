package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.exception.PlacaDuplicadaException;
import co.edu.uniquindio.parkuq.exception.VehiculoNoIngresadoException;
import co.edu.uniquindio.parkuq.model.*;
import co.edu.uniquindio.parkuq.model.enums.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Servicio encargado del registro de ingreso y salida de vehículos.
 *
 * @author Equipo PARKUQ
 */
public class RegistroVehiculosService {

    private final List<Vehiculo> vehiculos;
    private final List<Tarifa> tarifas;
    private final List<Usuario> usuarios;
    private final EspaciosService espaciosService;
    private final ReportesService reportesService;

    public RegistroVehiculosService(List<Vehiculo> vehiculos,
                                    List<Tarifa> tarifas,
                                    List<Usuario> usuarios,
                                    EspaciosService espaciosService,
                                    ReportesService reportesService) {
        this.vehiculos = vehiculos;
        this.tarifas = tarifas;
        this.usuarios = usuarios;
        this.espaciosService = espaciosService;
        this.reportesService = reportesService;
    }

    /**
     * Registra el ingreso de un vehículo al parqueadero.
     *
     * @throws PlacaDuplicadaException      si la placa ya está activa dentro del parqueadero
     * @throws EspacioNoDisponibleException si no hay espacio disponible para el tipo de vehículo
     */
    public void registrarIngreso(Vehiculo vehiculo)
            throws PlacaDuplicadaException, EspacioNoDisponibleException {

        // Verificar placa duplicada
        boolean placaActiva = vehiculos.stream()
                .anyMatch(v -> v.getPlaca().equalsIgnoreCase(vehiculo.getPlaca())
                        && v.getEstado() == EstadoVehiculo.DENTRO);
        if (placaActiva) {
            throw new PlacaDuplicadaException("La placa " + vehiculo.getPlaca() + " ya está registrada dentro.");
        }

        // Determinar tipo de espacio según tipo de vehículo
        TipoEspacio tipoEspacio = resolverTipoEspacio(vehiculo);

        // Buscar y asignar espacio
        Espacio espacio = espaciosService.buscarEspacioDisponible(tipoEspacio);
        espacio.setEstado(EstadoEspacio.OCUPADO);
        espacio.setVehiculoAsignado(vehiculo);

        // Configurar el vehículo
        vehiculo.setHoraIngreso(LocalDateTime.now());
        vehiculo.setEspacioAsignado(espacio);
        vehiculo.setEstado(EstadoVehiculo.DENTRO);

        vehiculos.add(vehiculo);
    }

    /**
     * Registra la salida de un vehículo y retorna el monto a cobrar.
     *
     * @throws VehiculoNoIngresadoException si la placa no está registrada como DENTRO
     */
    public double registrarSalida(String placa) throws VehiculoNoIngresadoException {
        Vehiculo vehiculo = buscarPorPlaca(placa);
        if (vehiculo == null) {
            throw new VehiculoNoIngresadoException("No hay vehículo con placa " + placa + " dentro del parqueadero.");
        }

        // Calcular horas de permanencia (mínimo 1 hora)
        long horas = ChronoUnit.HOURS.between(vehiculo.getHoraIngreso(), LocalDateTime.now());

        // Obtener tarifa del tipo de vehículo
        Tarifa tarifa = obtenerTarifa(vehiculo);

        // Calcular costo base
        double costo = vehiculo.calcularTarifa(horas, tarifa);

        // Aplicar descuento si el conductor está registrado como usuario autorizado
        double descuento = obtenerDescuentoUsuario(vehiculo.getIdentificacionConductor());
        costo = costo * (1 - descuento);

        // Liberar el espacio
        Espacio espacio = vehiculo.getEspacioAsignado();
        if (espacio != null) {
            espacio.setEstado(EstadoEspacio.DISPONIBLE);
            espacio.setVehiculoAsignado(null);
        }

        // Marcar el vehículo como salido
        vehiculo.setEstado(EstadoVehiculo.SALIO);

        // Registrar ingreso económico
        reportesService.registrarIngreso(costo);

        return costo;
    }

    /**
     * Busca un vehículo con estado DENTRO por su placa.
     *
     * @return vehículo encontrado o null
     */
    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa)
                        && v.getEstado() == EstadoVehiculo.DENTRO)
                .findFirst()
                .orElse(null);
    }

    // ---- helpers ----

    private TipoEspacio resolverTipoEspacio(Vehiculo v) {
        if (v instanceof Carro)       return TipoEspacio.CARRO;
        if (v instanceof Motocicleta) return TipoEspacio.MOTOCICLETA;
        if (v instanceof Bicicleta)   return TipoEspacio.BICICLETA;
        return TipoEspacio.CARRO;
    }

    private TipoVehiculo resolverTipoVehiculo(Vehiculo v) {
        if (v instanceof Carro)       return TipoVehiculo.CARRO;
        if (v instanceof Motocicleta) return TipoVehiculo.MOTOCICLETA;
        if (v instanceof Bicicleta)   return TipoVehiculo.BICICLETA;
        return TipoVehiculo.CARRO;
    }

    private Tarifa obtenerTarifa(Vehiculo vehiculo) {
        TipoVehiculo tipo = resolverTipoVehiculo(vehiculo);
        return tarifas.stream()
                .filter(t -> t.getTipoVehiculo() == tipo)
                .findFirst()
                .orElse(new Tarifa(tipo, 3000, 0)); // tarifa por defecto
    }

    private double obtenerDescuentoUsuario(String identificacion) {
        return usuarios.stream()
                .filter(u -> u.getIdentificacion().equalsIgnoreCase(identificacion))
                .findFirst()
                .map(Usuario::obtenerDescuento)
                .orElse(0.0);
    }
}
