package co.edu.uniquindio.parkuq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test de verificación básica del entorno JUnit 5.
 *
 * @author Equipo PARKUQ
 */
class AppTest {

    @Test
    void junitFunciona() {
        assertEquals(2, 1 + 1, "JUnit 5 debe estar correctamente configurado");
    }
}
