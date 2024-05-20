package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.control.simuladorDeVida;
import excepciones.sinFicherosDePartidaException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
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

    @FXML
    private ListView<String> listaDeFicheros = new ListView<>();

    @FXML
    protected void onBotonNuevoClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("menuConfiguracionInicio-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBotonCargarPartidaClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("cargarJuego-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBotonCargarFicheroClick (ActionEvent event) {
        try {
            if (!listaDeFicheros.getSelectionModel().isEmpty()) {
                Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stageActual.close();
                String archivo = listaDeFicheros.getSelectionModel().getSelectedItem();
                DataModel model = DataModel.cargar(archivo);
                simuladorDeVida juegoActual = new simuladorDeVida(model, false);
                tableroController controladorTablero = new tableroController(model, juegoActual);
                controladorTablero.crearTablero(juegoActual.getTablero());
            }
        } catch (IOException e){
            log.error("No se ha encontrado la ruta especificada para cargar el archivo");
        }
    }

    @FXML
    protected void onBotonVolverMenuClick (ActionEvent event) throws IOException {
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
            listaDeFicheros.getItems().addAll(getNombreFicheros());
        } catch (sinFicherosDePartidaException e) {
            log.info("No se han encontrado ficheros de partida para cargar.");
        }
    }
}