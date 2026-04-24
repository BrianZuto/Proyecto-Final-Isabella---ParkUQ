package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.exception.PlacaDuplicadaException;
import co.edu.uniquindio.parkuq.exception.VehiculoNoIngresadoException;
import co.edu.uniquindio.parkuq.model.Vehiculo;

/**
 * Servicio encargado del registro de ingreso y salida de vehículos.
 *
 * @author Equipo PARKUQ
 */
public class RegistroVehiculosService {

    /**
     * Registra el ingreso de un vehículo al parqueadero.
     *
     * TODO [Isabella] – implementar: verificar placa duplicada, asignar espacio,
     *                   registrar horaIngreso y actualizar estado del espacio.
     *
     * @param vehiculo vehículo que ingresa
     * @throws PlacaDuplicadaException      si la placa ya está activa
     * @throws EspacioNoDisponibleException si no hay espacio para el tipo de vehículo
     */
    public void registrarIngreso(Vehiculo vehiculo)
            throws PlacaDuplicadaException, EspacioNoDisponibleException {
        // TODO [Isabella]: implementar
    }

    /**
     * Registra la salida de un vehículo y calcula el cobro.
     *
     * TODO [Isabella] – implementar: buscar por placa, calcular tarifa,
     *                   liberar espacio y actualizar estado del vehículo.
     *
     * @param placa placa del vehículo que sale
     * @return monto a cobrar
     * @throws VehiculoNoIngresadoException si la placa no está registrada como DENTRO
     */
    public double registrarSalida(String placa) throws VehiculoNoIngresadoException {
        // TODO [Isabella]: implementar
        return 0;
    }

    /**
     * Busca un vehículo por placa entre los que están DENTRO.
     *
     * TODO [Isabella] – implementar.
     *
     * @param placa placa a buscar
     * @return vehículo encontrado o null
     */
    public Vehiculo buscarPorPlaca(String placa) {
        // TODO [Isabella]: implementar
        return null;
    }
}
