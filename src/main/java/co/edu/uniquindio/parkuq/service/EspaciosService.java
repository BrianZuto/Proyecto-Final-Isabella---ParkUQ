package co.edu.uniquindio.parkuq.service;

import co.edu.uniquindio.parkuq.exception.EspacioNoDisponibleException;
import co.edu.uniquindio.parkuq.model.Espacio;
import co.edu.uniquindio.parkuq.model.Parqueadero;
import co.edu.uniquindio.parkuq.model.enums.EstadoEspacio;
import co.edu.uniquindio.parkuq.model.enums.TipoEspacio;

import java.util.List;

/**
 * Servicio de gestión de espacios del parqueadero.
 *
 * @author Equipo PARKUQ
 */
public class EspaciosService {

    private final List<Espacio> espacios;

    public EspaciosService(List<Espacio> espacios) {
        this.espacios = espacios;
    }

    /**
     * Busca el primer espacio disponible para el tipo indicado.
     */
    public Espacio buscarEspacioDisponible(TipoEspacio tipo) throws EspacioNoDisponibleException {
        return espacios.stream()
                .filter(e -> e.getTipoEspacio() == tipo && e.getEstado() == EstadoEspacio.DISPONIBLE)
                .findFirst()
                .orElseThrow(() -> new EspacioNoDisponibleException(
                        "No hay espacios disponibles para " + tipo));
    }

    /**
     * Retorna todos los espacios del parqueadero.
     */
    public List<Espacio> listarEspacios() {
        return espacios;
    }

    /**
     * Actualiza el estado de un espacio (operación en memoria; el objeto ya fue modificado).
     */
    public void actualizarEstado(Espacio espacio) {
        // El objeto está en la lista; ya fue mutado por el llamador.
    }

    /**
     * Registra un nuevo espacio en el parqueadero.
     */
    public void registrarEspacio(Espacio espacio) {
        espacios.add(espacio);
    }

    /**
     * Deshabilita un espacio: cambia su estado a FUERA_DE_SERVICIO.
     */
    public void deshabilitarEspacio(String codigo) {
        espacios.stream()
                .filter(e -> e.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .ifPresent(e -> e.setEstado(EstadoEspacio.FUERA_DE_SERVICIO));
    }

    /**
     * Habilita un espacio previamente deshabilitado.
     */
    public void habilitarEspacio(String codigo) {
        espacios.stream()
                .filter(e -> e.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .ifPresent(e -> {
                    if (e.getEstado() == EstadoEspacio.FUERA_DE_SERVICIO) {
                        e.setEstado(EstadoEspacio.DISPONIBLE);
                    }
                });
    }

    /** Cantidad de espacios disponibles por tipo. */
    public long contarDisponiblesPorTipo(TipoEspacio tipo) {
        return espacios.stream()
                .filter(e -> e.getTipoEspacio() == tipo && e.getEstado() == EstadoEspacio.DISPONIBLE)
                .count();
    }
}
