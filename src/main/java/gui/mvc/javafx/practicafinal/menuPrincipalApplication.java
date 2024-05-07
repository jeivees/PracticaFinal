package gui.mvc.javafx.practicafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class menuPrincipalApplication extends Application {
    /**
     * la probabilidad de que aparezca un recurso es global o para cada casilla?
     * la reproduccion cuando hay 3 individuos se calcula entre todas las combinaciones de ellos o por parejas? en caso de parejas cual de las dos probabilidades se usa?
     * añadir porcentaje de mejora de poblacion a configuracion individuo
     * la probabilidad de reproduccion y clonacion es distinta para cada tipo de individuo?
     * mutación o clonación?
     */



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(menuPrincipalApplication.class.getResource("menuPrincipal-vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Simulador de Vida");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}