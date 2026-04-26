module co.edu.uniquindio.parkuq {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.parkuq to javafx.fxml;
    opens co.edu.uniquindio.parkuq.ui.controller to javafx.fxml;

    exports co.edu.uniquindio.parkuq;
    exports co.edu.uniquindio.parkuq.model;
    exports co.edu.uniquindio.parkuq.model.enums;
    exports co.edu.uniquindio.parkuq.service;
    exports co.edu.uniquindio.parkuq.exception;
}
