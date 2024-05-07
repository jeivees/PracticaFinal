package gui.mvc.javafx.practicafinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    Tab tabActual = new Tab();
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
            20,20,20,10,10,10,
            1,3,2,15,10,
            10, 10);
    private configuracionDataModelProperties model = new configuracionDataModelProperties(original);

    @FXML
    protected void onBotonGuardarClick() {
        log.debug("Se ha guardado la configuración");
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPaneConfiguracion.getSelectionModel().selectedItemProperty().addListener((_, _, newTab) -> {
            log.debug("Se ha detectado un cambio en el tabPane");
            if (newTab != null) tabActual = newTab;
        });

        SpinnerValueFactory<Integer> TurnosVidaInicialesVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        SpinnerValueFactory<Integer> IncrementoAguaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        SpinnerValueFactory<Integer> IncrementoComidaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        SpinnerValueFactory<Integer> IncrementoMontañaVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        SpinnerValueFactory<Integer> FilasTableroVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        SpinnerValueFactory<Integer> ColumnasTableroVF =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);

        TurnosVidaInicialesSpinner.setValueFactory(TurnosVidaInicialesVF);
        IncrementoTurnosAguaSpinner.setValueFactory(IncrementoAguaVF);
        IncrementoTurnosComidaSpinner.setValueFactory(IncrementoComidaVF);
        IncrementoTurnosMontañaSpinner.setValueFactory(IncrementoMontañaVF);
        FilasTableroSpinner.setValueFactory(FilasTableroVF);
        ColumnasTableroSpinner.setValueFactory(ColumnasTableroVF);

        if (model != null) {
            TurnosVidaInicialesSpinner.getValueFactory().valueProperty().bindBidirectional(model.TurnosVidaInicialesProperty().asObject());
            IncrementoTurnosAguaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosAguaProperty().asObject());
            IncrementoTurnosComidaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosComidaProperty().asObject());
            IncrementoTurnosMontañaSpinner.getValueFactory().valueProperty().bindBidirectional(model.IncrementoTurnosMontañaProperty().asObject());
            FilasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(model.FilasTableroProperty().asObject());
            ColumnasTableroSpinner.getValueFactory().valueProperty().bindBidirectional(model.ColumnasTableroProperty().asObject());
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