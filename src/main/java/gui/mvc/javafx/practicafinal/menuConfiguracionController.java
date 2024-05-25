package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.control.simuladorDeVida;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private Slider ProbMejoraNormalSlider = new Slider();

    @FXML
    private Slider ProbMejoraAvanzadoSlider = new Slider();

    @FXML
    private Spinner<Integer> ProbAparRecursoSpinner = new Spinner<>();
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

    private DataModel model = new DataModel(
            10, 50, 10, 50,25,
            3,15,20,20,20,
            10,10,10,3,5,
            7, 25, 8, 8, 10, 0);
    private DataModelProperties properties = new DataModelProperties(model);

    public menuConfiguracionController () {}

    protected void updateGUIwithModel() {
        ProbReproIndividuoSlider.valueProperty().bindBidirectional(properties.ProbReproIndividuoProperty());
        ProbClonIndividuoSlider.valueProperty().bindBidirectional(properties.ProbClonIndividuoProperty());
        ProbMejoraNormalSlider.valueProperty().bindBidirectional(properties.ProbMejoraToNormalProperty());
        ProbMejoraAvanzadoSlider.valueProperty().bindBidirectional(properties.ProbMejoraToAvanzadoProperty());

        ProbAparAguaSlider.valueProperty().bindBidirectional(properties.ProbAparAguaProperty());
        ProbAparComidaSlider.valueProperty().bindBidirectional(properties.ProbAparComidaProperty());
        ProbAparMontañaSlider.valueProperty().bindBidirectional(properties.ProbAparMontañaProperty());
        ProbAparTesoroSlider.valueProperty().bindBidirectional(properties.ProbAparTesoroProperty());
        ProbAparBibliotecaSlider.valueProperty().bindBidirectional(properties.ProbAparBibliotecaProperty());
        ProbAparPozoSlider.valueProperty().bindBidirectional(properties.ProbAparPozoProperty());
        IncrementoProbReproSlider.valueProperty().bindBidirectional(properties.IncrementoProbReproProperty());
        IncrementoProbClonSlider.valueProperty().bindBidirectional(properties.IncrementoProbClonProperty());

        TurnosVidaInicialesSpinner.getValueFactory().valueProperty().bindBidirectional(properties.TurnosVidaInicialesProperty());

        ProbAparRecursoSpinner.getValueFactory().valueProperty().bindBidirectional(properties.ProbAparRecursoProperty());
        TurnosInicialesRecursoSpinner.getValueFactory().valueProperty().bindBidirectional(properties.TurnosInicialesRecursoProperty());
        IncrementoTurnosAguaSpinner.getValueFactory().valueProperty().bindBidirectional(properties.IncrementoTurnosAguaProperty());
        IncrementoTurnosComidaSpinner.getValueFactory().valueProperty().bindBidirectional(properties.IncrementoTurnosComidaProperty());
        IncrementoTurnosMontañaSpinner.getValueFactory().valueProperty().bindBidirectional(properties.IncrementoTurnosMontañaProperty());

        FilasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(properties.FilasTableroProperty());
        ColumnasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(properties.ColumnasTableroProperty());
    }

    protected void initializeSpinners () {
        SpinnerValueFactory<Integer> TurnosVidaInicialesVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000);

        SpinnerValueFactory<Integer> ProbAparRecursoVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);

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
        ProbAparRecursoSpinner.setValueFactory(ProbAparRecursoVF);
        TurnosInicialesRecursoSpinner.setValueFactory(TurnosInicialesRecursoVF);
        IncrementoTurnosAguaSpinner.setValueFactory(IncrementoAguaVF);
        IncrementoTurnosComidaSpinner.setValueFactory(IncrementoComidaVF);
        IncrementoTurnosMontañaSpinner.setValueFactory(IncrementoMontañaVF);
        FilasTableroSpinner.setValueFactory(FilasTableroVF);
        ColumnasTableroSpinner.setValueFactory(ColumnasTableroVF);


        añadirFiltroSpinner(TurnosVidaInicialesSpinner);
        añadirFiltroSpinner(ProbAparRecursoSpinner);
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

    @FXML
    protected void onBotonGuardarClick(ActionEvent event) throws IOException{
        if (Window.getWindows().getFirst() != ((Node) event.getSource()).getScene().getWindow()) { // si la ventana principal es la de configuracion o es otra (el tablero)
            Stage ventanaTablero = (Stage) Window.getWindows().getFirst();
            casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ventanaTablero.getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
            model = casilla00.getModel();
            properties.setModel(model);
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        if (!model.isPausado()) {
            confirmacion.initOwner(Stage.getWindows().getFirst());
            confirmacion.setTitle("Crear nueva partida");
            confirmacion.setHeaderText("Estás a punto de guardar los ajustes y crear una nueva partida");
            confirmacion.setContentText("¿Estás seguro de que quieres continuar?");
        } else {
            confirmacion.initOwner(Stage.getWindows().get(1));
            confirmacion.setTitle("Continuar con el juego");
            confirmacion.setHeaderText("Estás a punto de guardar los ajustes para continuar la partida");
            confirmacion.setContentText("¿Estás seguro de que quieres continuar?");
        }

        if(confirmacion.showAndWait().get() == ButtonType.OK) {
            continuarJuego(event);
        }
    }

    private void continuarJuego(ActionEvent event) throws IOException {
        properties.commit();

        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageActual.close();

        if (!model.isPausado()) { // en caso de que sea una partida nueva
            empezarNuevoJuego();
            log.debug("Se ha creado un nuevo juego");
        } else {
            log.debug("Se ha guardado la configuración");
        }
    }

    private void empezarNuevoJuego () throws IOException {
        model.setTurno(0);
        simuladorDeVida juegoActual = new simuladorDeVida(model, false);
        tableroController controladorTablero = new tableroController(model, juegoActual);
        controladorTablero.crearTablero(juegoActual.getTablero());
        model.setPausado(true);
    }

    @FXML
    protected void onBotonReiniciarClick() {
        properties.rollback(tabActual);
        log.debug("Los valores por defecto han sido reestablecidos");
    }

    @FXML
    protected void onBotonVolverClick (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menuPrincipal-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel original) {
        this.model = original;
    }

    public void setControllerValues (DataModel model) {
        this.model = model;
        this.properties = new DataModelProperties(model);
        initializeController();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPaneConfiguracion.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            log.debug("Se ha detectado un cambio en el tabPane");
            if (newTab != null) tabActual = newTab;
        });

        initializeController();
    }
    private void initializeController () {
        initializeSpinners();
        if (properties != null) {
            this.updateGUIwithModel();
        }
    }
}