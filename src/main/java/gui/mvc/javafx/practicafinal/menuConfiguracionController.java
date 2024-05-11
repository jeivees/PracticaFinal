package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class menuConfiguracionController implements Initializable {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    @FXML
    TabPane tabPaneConfiguracion = new TabPane();
    Tab tabActual;

    @FXML
    private Spinner<Integer> TurnosVidaInicialesSpinner = new Spinner<>();
    @FXML
    private Slider ProbReproIndividuoSlider = new Slider();
    @FXML
    private Slider ProbClonIndividuoSlider = new Slider();
    @FXML
    private Slider ProbAparAguaSlider = new Slider();
    @FXML
    private Slider ProbAparComidaSlider = new Slider();
    @FXML
    private Slider ProbAparMontañaSlider = new Slider();
    @FXML
    private Slider ProbAparTesoroSlider = new Slider();
    @FXML
    private Slider ProbAparBibliotecaSlider = new Slider();
    @FXML
    private Slider ProbAparPozoSlider = new Slider();
    @FXML
    private Spinner<Integer> TurnosInicialesRecursoSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> IncrementoTurnosAguaSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> IncrementoTurnosComidaSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> IncrementoTurnosMontañaSpinner = new Spinner<>();
    @FXML
    private Slider IncrementoProbReproSlider = new Slider();
    @FXML
    private Slider IncrementoProbClonSlider = new Slider();
    @FXML
    private Spinner<Integer> FilasTableroSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> ColumnasTableroSpinner = new Spinner<>();


    private configuracionDataModel original = new configuracionDataModel(5, 50, 30,
            10,20,20,20,10,10,10,
            1,3,2,15,10,
            10, 10, 10, 10, 0);
    private configuracionDataModelProperties model = new configuracionDataModelProperties(original);

    private simuladorDeVida juegoActual;
    private tableroController tableroController;

    public menuConfiguracionController () {}
    public menuConfiguracionController (configuracionDataModel original) {
        this.original = original;
        this.model = new configuracionDataModelProperties(original);
    }
    @FXML
    protected void onBotonGuardarClick(ActionEvent event) throws IOException{
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Continuar al juego");
        confirmacion.setHeaderText("Estás a punto de guardar los ajustes y continuar a la partida");
        confirmacion.setContentText("¿Estás seguro de que quieres continuar?");

        if(confirmacion.showAndWait().get() == ButtonType.OK) {
            continuarJuego(event);
        }
    }

    private void continuarJuego(ActionEvent event) throws IOException {
        if (!original.isPausado()) { // en caso de que sea una partida nueva
            model.commit();

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            empezarNuevoJuego();
            log.debug("Se ha creado un nuevo juego");
        } else {

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            model.commit();
            log.debug("Se ha guardado la configuración");

        }
    }

    private void empezarNuevoJuego () throws IOException {
        original.setTurno(0);
        juegoActual = new simuladorDeVida(original);
        tableroController = new tableroController();
        tableroController.setModel(original);
        tableroController.setJuegoActual(juegoActual);
        tableroController.crearTablero();
    }



    public void mostrarMenuConfiguracion() throws IOException{
        Parent root;
        if (!original.isPausado()) {
            root = FXMLLoader.load(getClass().getResource("menuConfiguracionInicio-vista.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("menuConfiguracionPausa-vista.fxml"));
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBotonReiniciarClick() {
        model.rollback(tabActual);
        log.debug("Los valores por defecto han sido reestablecidos");
    }

    protected void updateGUIwithModel() {
        ProbReproIndividuoSlider.valueProperty().bindBidirectional(model.ProbReproIndividuoProperty());
        ProbClonIndividuoSlider.valueProperty().bindBidirectional(model.ProbClonIndividuoProperty());
        ProbAparAguaSlider.valueProperty().bindBidirectional(model.ProbAparAguaProperty());
        ProbAparComidaSlider.valueProperty().bindBidirectional(model.ProbAparComidaProperty());
        ProbAparMontañaSlider.valueProperty().bindBidirectional(model.ProbAparMontañaProperty());
        ProbAparTesoroSlider.valueProperty().bindBidirectional(model.ProbAparTesoroProperty());
        ProbAparBibliotecaSlider.valueProperty().bindBidirectional(model.ProbAparBibliotecaProperty());
        ProbAparPozoSlider.valueProperty().bindBidirectional(model.ProbAparPozoProperty());
        IncrementoProbReproSlider.valueProperty().bindBidirectional(model.IncrementoProbReproProperty());
        IncrementoProbClonSlider.valueProperty().bindBidirectional(model.IncrementoProbClonProperty());
        TurnosVidaInicialesSpinner.getValueFactory().valueProperty().bindBidirectional(model.TurnosVidaInicialesProperty());
        TurnosInicialesRecursoSpinner.getValueFactory().valueProperty().bindBidirectional(model.TurnosInicialesRecursoProperty());
        IncrementoTurnosAguaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosAguaProperty());
        IncrementoTurnosComidaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosComidaProperty());
        IncrementoTurnosMontañaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosMontañaProperty());
        FilasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(model.FilasTableroProperty());
        ColumnasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(model.ColumnasTableroProperty());
    }

    protected void initializeSpinners () {
        SpinnerValueFactory<Integer> TurnosVidaInicialesVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);

        SpinnerValueFactory<Integer> TurnosInicialesRecursoVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);

        SpinnerValueFactory<Integer> IncrementoAguaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);

        SpinnerValueFactory<Integer> IncrementoComidaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);

        SpinnerValueFactory<Integer> IncrementoMontañaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);

        SpinnerValueFactory<Integer> FilasTableroVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);

        SpinnerValueFactory<Integer> ColumnasTableroVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);

        TurnosVidaInicialesSpinner.setValueFactory(TurnosVidaInicialesVF);
        TurnosInicialesRecursoSpinner.setValueFactory(TurnosInicialesRecursoVF);
        IncrementoTurnosAguaSpinner.setValueFactory(IncrementoAguaVF);
        IncrementoTurnosComidaSpinner.setValueFactory(IncrementoComidaVF);
        IncrementoTurnosMontañaSpinner.setValueFactory(IncrementoMontañaVF);
        FilasTableroSpinner.setValueFactory(FilasTableroVF);
        ColumnasTableroSpinner.setValueFactory(ColumnasTableroVF);


        añadirFiltroSpinner(TurnosVidaInicialesSpinner);
        añadirFiltroSpinner(TurnosInicialesRecursoSpinner);
        añadirFiltroSpinner(IncrementoTurnosAguaSpinner);
        añadirFiltroSpinner(IncrementoTurnosComidaSpinner);
        añadirFiltroSpinner(IncrementoTurnosMontañaSpinner);
        añadirFiltroSpinner(TurnosVidaInicialesSpinner);
        añadirFiltroSpinner(FilasTableroSpinner);
        añadirFiltroSpinner(ColumnasTableroSpinner);
    }

    private void añadirFiltroSpinner (Spinner<Integer> spinner) {
        spinner.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPaneConfiguracion.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            log.debug("Se ha detectado un cambio en el tabPane");
            if (newTab != null) tabActual = newTab;
        });

        initializeSpinners();

        if (model != null) {
            this.updateGUIwithModel();
        }
    }

    @FXML
    protected void onBotonVolverClick (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menuPrincipal-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}