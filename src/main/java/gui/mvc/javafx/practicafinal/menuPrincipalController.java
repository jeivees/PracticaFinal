package gui.mvc.javafx.practicafinal;

import excepciones.sinFicherosDePartidaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Label;
import es.uah.matcomp.mp.simulaciondevida.*;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class menuPrincipalController implements Initializable {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label Titulo;
    @FXML
    private ListView<String> listaDeFicheros;
    private File[] archivosDePartida;
    private String archivoActual;

    @FXML
    protected void onNuevoButtonClick() {

    }

    @FXML
    protected void onCargarPartidaButtonClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("cargarJuego-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onCargarFicheroButtonClick () {
        simuladorDeVida juegoGuardado = new simuladorDeVida();
        juegoGuardado.cargarJuego().comenzar();
    }

    @FXML
    protected void onVolverMenuButtonClick (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menuPrincipal-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String[] getNombreFicheros () throws sinFicherosDePartidaException{
        File carpetaDeArchivos = new File("archivosDePartida");
        File[] archivos = carpetaDeArchivos.listFiles();
        if (archivos != null) {
            String[] nombresDeArchivos = new String[archivos.length];
            for (int i = 0; i != archivos.length; i++) {
                nombresDeArchivos[i] = archivos[i].getName();
            }
            return nombresDeArchivos;
        }
        throw new sinFicherosDePartidaException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (listaDeFicheros != null) {
                listaDeFicheros.getItems().addAll(getNombreFicheros());

                listaDeFicheros.getSelectionModel().selectedItemProperty().addListener((_, _, _) ->
                        archivoActual = listaDeFicheros.getSelectionModel().getSelectedItem());
            }
        } catch (sinFicherosDePartidaException e) {
            log.info("No se han encontrado ficheros de partida para cargar.");
        }
    }
}