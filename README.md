# PARKUQ – Sistema de Gestión de Parqueadero Inteligente

Sistema de gestión de parqueadero para la **Universidad del Quindío**, desarrollado en Java 21 con JavaFX como proyecto de la materia **Programación I**.

---

## Equipo de desarrollo

| Integrante      | Módulo asignado                          |
|-----------------|------------------------------------------|
| **Isabella**    | Vehículos y Tarifas                      |
| **Juan David**  | Usuarios y Seguridad                     |
| **Reinel**      | Espacios, Reportes e Infraestructura     |

---

## Requisitos previos

- Java 21 (JDK)
- Apache Maven 3.9+
- IntelliJ IDEA 2023+ (recomendado)

---

## Cómo ejecutar

```bash
mvn clean javafx:run
```

---

## Cómo correr los tests

```bash
mvn test
```

---

## Estructura de ramas Git

| Rama                            | Responsable  | Descripción                        |
|---------------------------------|--------------|------------------------------------|
| `main`                          | Todos        | Código estable, revisado           |
| `feature/isabella-vehiculos`    | Isabella     | Módulo Vehículos y Tarifas         |
| `feature/juandavid-usuarios`    | Juan David   | Módulo Usuarios y Seguridad        |
| `feature/reinel-espacios`       | Reinel       | Espacios, Reportes e Infraestructura |

---

## Estructura del proyecto

```
src/
├── main/
│   ├── java/co/edu/uniquindio/parkuq/
│   │   ├── App.java                    # Punto de entrada JavaFX
│   │   ├── model/                      # Clases del dominio
│   │   │   ├── enums/                  # Enumeraciones del sistema
│   │   │   ├── Vehiculo.java (abstracta)
│   │   │   ├── Usuario.java  (abstracta)
│   │   │   ├── Espacio.java
│   │   │   ├── Tarifa.java
│   │   │   └── Parqueadero.java        # Fachada principal
│   │   ├── service/                    # Lógica de negocio
│   │   ├── exception/                  # Excepciones personalizadas
│   │   └── ui/controller/             # Controladores FXML
│   └── resources/co/edu/uniquindio/parkuq/
│       ├── fxml/                       # Vistas FXML
│       └── styles/                     # Hojas de estilo CSS
└── test/
    └── java/co/edu/uniquindio/parkuq/  # Tests JUnit 5
docs/                                   # Diagramas y documentación
```
