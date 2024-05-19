package gui.mvc.javafx.practicafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class menuPrincipalApplication extends Application {
    /**
     * verificar que la suma de las probabilidades de los recursos no sea mayor que 100
     * añadir boton salir del juego menú principal
     * en las alertas, confirmaciones setoncloserequest y en todas las pantallas setoncloserequest
     * añadir boton eliminar partida menú carga
     * arreglar esquivar montañas
     * establecer generación correctamente
     */


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(menuPrincipalApplication.class.getResource("menuPrincipal-vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Simulador de Vida");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}