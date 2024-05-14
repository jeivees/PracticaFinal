package gui.mvc.javafx.practicafinal;

import excepciones.sinFicherosDePartidaException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private ListView<String> listaDeFicheros = new ListView<>();
    private File[] archivosDePartida;
    private String archivoActual;

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
    protected void onBotonCargarFicheroClick () {
        try {
            String archivo = listaDeFicheros.getSelectionModel().getSelectedItem();
            DataModel model = DataModel.cargar(archivo);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuConfiguracionInicio-vista.fxml"));
            Parent root = loader.load();
            menuConfiguracionController controller = loader.getController();
            controller.setControllerValues(model);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e){
            log.error("No se ha podido cargar el archivo, no se encuentra la ruta especificada");
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

            listaDeFicheros.getSelectionModel().selectedItemProperty().addListener((_, _, _) ->
                    archivoActual = listaDeFicheros.getSelectionModel().getSelectedItem());

        } catch (sinFicherosDePartidaException e) {
            log.info("No se han encontrado ficheros de partida para cargar.");
        }
    }
}