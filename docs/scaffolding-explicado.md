# Paso a paso: cómo se construyó el scaffolding de PARKUQ

---

## Fase 1 — Leer el terreno antes de tocar nada

Lo primero que se hace **no** es crear archivos. Es leer lo que ya existe:

```
ParkUQ/
├── pom.xml          ← tenía groupId: com.example, compilador Java 8, 10 dependencias innecesarias
├── src/main/java/
│   ├── module-info.java          ← módulo llamado com.example.parkuq
│   └── com/example/parkuq/
│       ├── HelloApplication.java
│       ├── HelloController.java
│       └── Launcher.java
└── src/main/resources/
    └── com/example/parkuq/hello-view.fxml
```

IntelliJ había generado un proyecto JavaFX genérico. El problema: el `groupId`, el nombre del módulo,
el paquete base y el `mainClass` del plugin eran todos `com.example` — incompatible con lo que se necesitaba.

**Decisión:** borrar los archivos del template viejo, reescribir el `pom.xml` y el `module-info.java`,
y construir todo desde cero sobre la base Maven correcta.

---

## Fase 2 — Crear la estructura de carpetas

Antes de escribir una sola clase, se crean todas las carpetas con un solo comando:

```bash
mkdir -p src/main/java/co/edu/uniquindio/parkuq/model/enums
mkdir -p src/main/java/co/edu/uniquindio/parkuq/service
mkdir -p src/main/java/co/edu/uniquindio/parkuq/exception
mkdir -p src/main/java/co/edu/uniquindio/parkuq/ui/controller
mkdir -p src/main/java/co/edu/uniquindio/parkuq/ui/view
mkdir -p src/main/resources/co/edu/uniquindio/parkuq/fxml
mkdir -p src/main/resources/co/edu/uniquindio/parkuq/styles
mkdir -p src/test/java/co/edu/uniquindio/parkuq
mkdir -p docs
```

**Por qué primero las carpetas:** Maven y Java son estrictos — el paquete `co.edu.uniquindio.parkuq.model`
debe existir como carpeta real `co/edu/uniquindio/parkuq/model/`. Si no existe la carpeta, el archivo no compila.

La carpeta `ui/view/` se deja vacía para que el equipo agregue los FXML futuros ahí.

---

## Fase 3 — El `pom.xml` (el corazón del proyecto)

Se reescribe el `pom.xml` completo. Estos son los cambios clave y por qué:

### 3.1 Coordenadas del proyecto

```xml
<groupId>co.edu.uniquindio</groupId>   <!-- dominio invertido de la UniQ -->
<artifactId>parkuq</artifactId>         <!-- nombre del artefacto en minúscula -->
<version>1.0-SNAPSHOT</version>         <!-- SNAPSHOT = en desarrollo, no liberado -->
```

### 3.2 Dependencias — solo las necesarias

El template de IntelliJ tenía 10 dependencias (controlsfx, formsfx, tilesfx, fxgl…).
Todas innecesarias para Programación I. Se eliminan y se dejan solo 3:

```xml
<!-- Para la interfaz gráfica -->
<dependency>javafx-controls</dependency>   <!-- botones, labels, textfield, etc. -->
<dependency>javafx-fxml</dependency>       <!-- carga archivos .fxml -->

<!-- Para las pruebas -->
<dependency>junit-jupiter</dependency>     <!-- JUnit 5 completo en una sola dependencia -->
```

> `junit-jupiter` (el artefacto "agregado") incluye API + Engine + Params.
> Es mejor que poner `junit-jupiter-api` y `junit-jupiter-engine` por separado.

### 3.3 Los tres plugins

**Plugin 1 — Compilador:**
```xml
<plugin>maven-compiler-plugin</plugin>
<source>21</source>
<target>21</target>
```
El template tenía `source/target 8` — Java 8. Con eso no funcionan `LocalDateTime`, records,
ni nada moderno. Se cambia a 21.

**Plugin 2 — JavaFX:**
```xml
<plugin>javafx-maven-plugin version 0.0.8</plugin>
<mainClass>co.edu.uniquindio.parkuq/co.edu.uniquindio.parkuq.App</mainClass>
```
El formato `modulo/clase` es obligatorio porque el proyecto usa `module-info.java`.
Sin la barra y el nombre del módulo, el plugin no sabe qué módulo cargar.

**Plugin 3 — Surefire:**
```xml
<plugin>maven-surefire-plugin version 3.2.5</plugin>
```
Sin este plugin configurado, `mvn test` no encuentra los tests de JUnit 5 automáticamente
en proyectos modulares. Surefire 3.x sí los detecta.

---

## Fase 4 — El `module-info.java`

Este archivo es el "pasaporte" del módulo Java. Le dice al JVM qué paquetes existen y cuáles son públicos:

```java
module co.edu.uniquindio.parkuq {
    requires javafx.controls;   // necesito usar Button, Label, etc.
    requires javafx.fxml;       // necesito cargar archivos .fxml

    opens co.edu.uniquindio.parkuq to javafx.fxml;
    // ↑ JavaFX necesita acceso por reflexión a App.java para instanciarla

    opens co.edu.uniquindio.parkuq.ui.controller to javafx.fxml;
    // ↑ JavaFX necesita acceso por reflexión a LoginController para inyectar @FXML

    exports co.edu.uniquindio.parkuq;
    // ↑ el paquete raíz es visible desde fuera del módulo
}
```

**La diferencia entre `opens` y `exports`:**
- `exports` → otros módulos pueden **usar** tus clases en tiempo de compilación.
- `opens` → JavaFX puede acceder a tus clases **en tiempo de ejecución por reflexión**
  (para inyectar los `@FXML` y para instanciar el `Application`).

Si se olvida el `opens` del controller, al correr la app aparece `IllegalAccessException`
aunque compile perfecto.

---

## Fase 5 — Los enums (base de todo el modelo)

Los enums van primero porque los usan todas las demás clases. Son simples pero definen
el vocabulario del sistema:

```
Vehiculo  usa → EstadoVehiculo, TipoVehiculo
Espacio   usa → TipoEspacio, EstadoEspacio
Usuario   usa → TipoUsuario
AutenticacionService retorna → Rol
```

Cada enum en su propio archivo dentro de `model/enums/`.
Ninguno depende del otro, así que se pueden crear en paralelo.

---

## Fase 6 — El modelo (en orden de dependencias)

El orden importa porque Java necesita que una clase exista para que otra la pueda referenciar.
El grafo de dependencias es:

```
enums ←── Tarifa
enums ←── Espacio ←──┐
enums ←── Vehiculo ───┤  (Espacio y Vehiculo se referencian mutuamente)
       └── Carro      │
       └── Motocicleta│
       └── Bicicleta  │
enums ←── Usuario     │
       └── Estudiante │
       └── Docente    │
       └── Administrativo
       └── Visitante
                      └── Parqueadero (usa todas las anteriores)
```

**¿Por qué `Vehiculo` y `Espacio` se referencian mutuamente?**

```java
// En Vehiculo:
protected Espacio espacioAsignado;   // un vehículo ocupa un espacio

// En Espacio:
private Vehiculo vehiculoAsignado;   // un espacio tiene un vehículo
```

Esto se llama **referencia circular** y Java lo permite perfectamente — solo hay que tener cuidado
de no crear dependencias circulares en los *constructores* (eso sí causa problemas).

### Las clases abstractas

`Vehiculo` y `Usuario` son `abstract` por una razón concreta: **nunca vas a crear un "vehículo genérico"**,
siempre un Carro, Motocicleta o Bicicleta específico. Lo mismo con Usuario.

```java
public abstract class Vehiculo {
    // atributos comunes a todos los vehículos
    protected String placa;
    protected LocalDateTime horaIngreso;
    // ...

    // cada subtipo calcula su tarifa diferente → OBLIGA a implementarlo
    public abstract double calcularTarifa(long horas, Tarifa tarifa);
}
```

El método `abstract` es un **contrato**: si creas `Carro extends Vehiculo` y no implementas
`calcularTarifa`, el compilador te lo impide. Es la forma de garantizar que no se puede olvidar implementarlo.

### El patrón Singleton en `Parqueadero`

```java
public class Parqueadero {
    private static Parqueadero instancia;  // una sola instancia

    private Parqueadero() { ... }  // nadie puede hacer "new Parqueadero()"

    public static Parqueadero getInstancia() {
        if (instancia == null) {
            instancia = new Parqueadero();  // se crea solo la primera vez
        }
        return instancia;
    }
}
```

**¿Por qué Singleton?** Porque físicamente hay un solo parqueadero. Si cada parte del código pudiera
hacer `new Parqueadero()`, habría múltiples listas de vehículos desincronizadas. Con Singleton,
todos acceden a la misma lista.

---

## Fase 7 — Las excepciones personalizadas

```java
public class EspacioNoDisponibleException extends Exception {
    public EspacioNoDisponibleException(String mensaje) {
        super(mensaje);  // pasa el mensaje a Exception
    }
}
```

Todas siguen el mismo patrón. **¿Por qué no usar `RuntimeException` o `IllegalArgumentException` genéricas?**

Porque con excepciones propias:
1. El código es **autodocumentado**: `throws EspacioNoDisponibleException` dice exactamente qué puede fallar.
2. Java **te obliga** a capturarlas (`try/catch`) porque extienden `Exception` (checked exception).
   Si usaras `RuntimeException` (unchecked), podrías olvidarte de manejarlas.

---

## Fase 8 — Los servicios (lógica de negocio)

Los servicios son clases que **hacen cosas** con el modelo. Separar la lógica del modelo
es el principio de **responsabilidad única**:

```
Vehiculo.java             → sabe QUÉ ES un vehículo (datos)
RegistroVehiculosService  → sabe QUÉ HACER con vehículos (lógica)
```

Cada método tiene la firma completa con el `throws` correcto y un `TODO` etiquetado:

```java
public void registrarIngreso(Vehiculo vehiculo)
        throws PlacaDuplicadaException, EspacioNoDisponibleException {
    // TODO [Isabella]: implementar
}
```

Así cada integrante sabe exactamente:
- Qué método implementar
- Qué excepciones lanzar
- Qué parámetros recibe y qué retorna

---

## Fase 9 — La capa UI

### `App.java` — el punto de entrada

```java
public class App extends Application {  // extiende Application de JavaFX
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Carga el archivo FXML
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("fxml/login.fxml")
        );

        // 2. Crea la escena con tamaño 800x600
        Scene scene = new Scene(loader.load(), 800, 600);

        // 3. Carga el CSS
        scene.getStylesheets().add(
            App.class.getResource("styles/style.css").toExternalForm()
        );

        // 4. Configura y muestra la ventana
        stage.setTitle("PARKUQ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);  // JavaFX inicia y llama a start()
    }
}
```

**¿Por qué `App.class.getResource("fxml/login.fxml")` y no una ruta absoluta?**

Porque `getResource` busca relativo al paquete de la clase. Como `App.java` está en
`co/edu/uniquindio/parkuq/` y el FXML está en `resources/co/edu/uniquindio/parkuq/fxml/`,
la ruta relativa `"fxml/login.fxml"` funciona en cualquier máquina, sin importar dónde
esté el proyecto instalado.

### `login.fxml` — la vista declarativa

```xml
<VBox fx:controller="co.edu.uniquindio.parkuq.ui.controller.LoginController"
      alignment="CENTER" spacing="20">

    <Label text="PARKUQ – Iniciar Sesión"/>
    <TextField fx:id="txtUsuario" promptText="Usuario"/>
    <PasswordField fx:id="txtContrasena" promptText="Contraseña"/>
    <Button text="Ingresar" onAction="#onIngresarClick"/>

</VBox>
```

- `fx:controller` → le dice al FXML qué clase Java lo maneja.
- `fx:id` → el identificador que Java usa para inyectar el campo con `@FXML`.
- `onAction="#onIngresarClick"` → cuando el botón se presiona, llama al método del controller.

### `LoginController.java` — el puente FXML ↔ Java

```java
public class LoginController {

    @FXML
    private TextField txtUsuario;      // JavaFX inyecta aquí el TextField del FXML
    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void onIngresarClick() {   // se llama cuando el botón es presionado
        String usuario    = txtUsuario.getText();
        String contrasena = txtContrasena.getText();
        // TODO [Juan David]: validar con AutenticacionService
    }
}
```

`@FXML` es la anotación que le dice a JavaFX: *"busca en el archivo FXML el elemento con
`fx:id="txtUsuario"` e inyéctalo en esta variable"*. Por eso el `module-info.java` necesita
el `opens ... to javafx.fxml` — sin él, JavaFX no puede hacer esa inyección.

---

## Fase 10 — El test de humo

```java
@Test
void junitFunciona() {
    assertEquals(2, 1 + 1, "JUnit 5 debe estar correctamente configurado");
}
```

Un test que siempre pasa, cuyo único propósito es confirmar que:
1. Maven encuentra los tests.
2. JUnit 5 está en el classpath.
3. Surefire los ejecuta correctamente.

Si este test falla, el problema es de configuración (`pom.xml`), no de lógica de negocio.

---

## El orden de creación en paralelo

Una vez que entiendes las dependencias, puedes crear archivos en paralelo así:

```
Ronda 1 — nada depende de ellos, se crean todos a la vez:
  ├── pom.xml
  ├── module-info.java
  ├── .gitignore
  ├── README.md
  ├── model/enums/TipoVehiculo.java
  ├── model/enums/EstadoVehiculo.java
  ├── model/enums/TipoEspacio.java
  ├── model/enums/EstadoEspacio.java
  ├── model/enums/TipoUsuario.java
  ├── model/enums/Rol.java
  ├── exception/EspacioNoDisponibleException.java
  ├── exception/PlacaDuplicadaException.java
  ├── exception/VehiculoNoIngresadoException.java
  └── exception/CredencialesInvalidasException.java

Ronda 2 — dependen solo de enums:
  ├── model/Tarifa.java
  ├── model/Espacio.java      ← referencia Vehiculo (Java acepta referencias hacia adelante)
  ├── model/Vehiculo.java     ← referencia Espacio y Tarifa
  └── model/Usuario.java

Ronda 3 — dependen de Vehiculo o Usuario:
  ├── model/Carro.java
  ├── model/Motocicleta.java
  ├── model/Bicicleta.java
  ├── model/Estudiante.java
  ├── model/Docente.java
  ├── model/Administrativo.java
  └── model/Visitante.java

Ronda 4 — dependen de todo el modelo:
  ├── model/Parqueadero.java
  ├── service/RegistroVehiculosService.java
  ├── service/EspaciosService.java
  ├── service/UsuariosService.java
  ├── service/AutenticacionService.java
  └── service/ReportesService.java

Ronda 5 — independientes entre sí:
  ├── App.java
  ├── ui/controller/LoginController.java
  ├── fxml/login.fxml
  ├── styles/style.css
  └── test/AppTest.java
```

> En la práctica, Java compila todo junto al final, así que el "orden" importa para que
> tú lo entiendas — el compilador resuelve las referencias solo.
