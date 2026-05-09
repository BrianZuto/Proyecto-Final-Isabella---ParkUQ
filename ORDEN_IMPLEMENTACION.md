# Orden de Implementación — ParkUQ

Orden para recrear el proyecto desde cero, de menor a mayor dependencia.

---

## FASE 1 — Configuración del proyecto

1. `pom.xml` — Dependencias JavaFX 21 + JUnit 5
2. `module-info.java` — Descriptor de módulos Java

---

## FASE 2 — Enumeraciones (sin dependencias)

3. `model/enums/TipoUsuario.java` — ESTUDIANTE, DOCENTE, ADMINISTRATIVO, VISITANTE
4. `model/enums/TipoVehiculo.java` — CARRO, MOTOCICLETA, BICICLETA
5. `model/enums/TipoEspacio.java` — CARRO, MOTOCICLETA, BICICLETA
6. `model/enums/EstadoVehiculo.java` — DENTRO, SALIO
7. `model/enums/EstadoEspacio.java` — DISPONIBLE, OCUPADO, FUERA_DE_SERVICIO
8. `model/enums/Rol.java` — OPERADOR, ADMINISTRADOR

---

## FASE 3 — Excepciones personalizadas (sin dependencias)

9. `exception/CredencialesInvalidasException.java`
10. `exception/EspacioNoDisponibleException.java`
11. `exception/PlacaDuplicadaException.java`
12. `exception/VehiculoNoIngresadoException.java`

---

## FASE 4 — Clases modelo base (abstractas)

13. `model/Tarifa.java` — tipoVehiculo, valorPorHora, descuento
14. `model/Vehiculo.java` — clase abstracta con `calcularTarifa()` abstracto
15. `model/Usuario.java` — clase abstracta con `obtenerDescuento()` abstracto

---

## FASE 5 — Subclases concretas de modelo

16. `model/Carro.java` — extiende Vehiculo
17. `model/Motocicleta.java` — extiende Vehiculo
18. `model/Bicicleta.java` — extiende Vehiculo
19. `model/Estudiante.java` — extiende Usuario (20% descuento)
20. `model/Docente.java` — extiende Usuario (30% descuento)
21. `model/Administrativo.java` — extiende Usuario (15% descuento)
22. `model/Visitante.java` — extiende Usuario (0% descuento)
23. `model/Espacio.java` — codigo, tipoEspacio, estado, vehiculoAsignado

---

## FASE 6 — Servicios (en este orden por dependencias)

24. `service/AutenticacionService.java` — login, roles, credenciales
25. `service/EspaciosService.java` — gestión de espacios
26. `service/UsuariosService.java` — gestión de usuarios
27. `service/ReportesService.java` — estadísticas e ingresos
28. `service/RegistroVehiculosService.java` — ingreso/salida (depende de los 4 anteriores)

---

## FASE 7 — Singleton principal

29. `model/Parqueadero.java` — Singleton fachada, instancia todos los servicios y datos iniciales

---

## FASE 8 — Punto de entrada JavaFX

30. `App.java` — extiende Application, carga login.fxml

---

## FASE 9 — Recursos CSS y FXML

31. `resources/.../styles/style.css` — todos los estilos visuales
32. `resources/.../fxml/login.fxml`
33. `resources/.../fxml/main.fxml`
34. `resources/.../fxml/ingreso.fxml`
35. `resources/.../fxml/salida.fxml`
36. `resources/.../fxml/espacios.fxml`
37. `resources/.../fxml/usuarios.fxml`
38. `resources/.../fxml/tarifas.fxml`
39. `resources/.../fxml/reportes.fxml`

---

## FASE 10 — Controladores (después de los FXML)

40. `ui/controller/LoginController.java`
41. `ui/controller/MainController.java`
42. `ui/controller/IngresoController.java`
43. `ui/controller/SalidaController.java`
44. `ui/controller/EspaciosController.java`
45. `ui/controller/UsuariosController.java`
46. `ui/controller/TarifasController.java`
47. `ui/controller/ReportesController.java`

---

## FASE 11 — Tests (al final, cuando todo esté escrito)

48. `test/.../AppTest.java`
49. `test/.../model/UsuarioTest.java`
50. `test/.../model/VehiculoTest.java`
51. `test/.../model/EspacioTest.java`
52. `test/.../service/AutenticacionServiceTest.java`
53. `test/.../service/EspaciosServiceTest.java`
54. `test/.../service/UsuariosServiceTest.java`
55. `test/.../service/RegistroVehiculosServiceTest.java`
56. `test/.../service/ReportesServiceTest.java`

---

## Regla general

Escribe siempre primero lo que no depende de nada (enums, excepciones), luego lo que depende de eso (modelos abstractos), luego las subclases, luego los servicios, y por último la UI. Los tests siempre al final cuando ya todo funciona.
