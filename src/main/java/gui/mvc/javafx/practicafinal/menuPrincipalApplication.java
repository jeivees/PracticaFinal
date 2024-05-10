package gui.mvc.javafx.practicafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class menuPrincipalApplication extends Application {
    /**
     * añadir turnos de vida de recursos y probabilidad de mejora para ambos individuos
     *
     */


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(menuPrincipalApplication.class.getResource("menuConfiguracionInicio-vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Simulador de Vida");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}