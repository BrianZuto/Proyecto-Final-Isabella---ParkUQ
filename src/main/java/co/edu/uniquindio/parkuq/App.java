package co.edu.uniquindio.parkuq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada de la aplicación PARKUQ.
 * Carga la vista de inicio de sesión y muestra la ventana principal.
 *
 * @author Equipo PARKUQ
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("fxml/login.fxml")
        );
        Scene scene = new Scene(loader.load(), 800, 600);
        scene.getStylesheets().add(
                App.class.getResource("styles/style.css").toExternalForm()
        );
        stage.setTitle("PARKUQ – Sistema de Parqueadero UniQuindío");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
