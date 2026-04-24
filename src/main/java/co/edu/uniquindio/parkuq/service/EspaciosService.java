package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.model.Espacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;

import java.util.List;

/**
 * Servicio de gestión de espacios del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class EspaciosService {

    /**
     * Busca y retorna el primer espacio disponible para el tipo indicado.
     *
     * TODO [Reinel] – implementar.
     *
     * @param tipo tipo de espacio requerido
     * @return espacio disponible
     * @throws EspacioNoDisponibleException si no hay espacios libres
     */
    public Espacio buscarEspacioDisponible(TipoEspacio tipo)
            throws EspacioNoDisponibleException {
        // TODO [Reinel]: implementar
        return null;
    }

    /**
     * Retorna todos los espacios del parqueadero.
     *
     * TODO [Reinel] – implementar.
     */
    public List<Espacio> listarEspacios() {
        // TODO [Reinel]: implementar
        return List.of();
    }

    /**
     * Actualiza el estado de un espacio (ocupado/disponible).
     *
     * TODO [Reinel] – implementar.
     *
     * @param espacio espacio a actualizar
     */
    public void actualizarEstado(Espacio espacio) {
        // TODO [Reinel]: implementar
    }
}
