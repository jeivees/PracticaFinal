package gui.mvc.javafx.practicafinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class menuConfiguracionController implements Initializable {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    @FXML
    private Spinner<Integer> TurnosVidaInicialSpinner;
    @FXML
    private Slider ProbReproIndividuoSlider;
    @FXML
    private Slider ProbClonIndividuoSlider;

    private configuracionDataModel original = new configuracionDataModel(5, 50, 30);
    private configuracionDataModelProperties model = new configuracionDataModelProperties(original);

    @FXML
    protected void onBotonGuardarClick() {
        model.commit();
        log.debug("Se ha guardado la configuración");
    }

    @FXML
    protected void onBotonReiniciarClick() {
        model.rollback();
        log.debug("Los valores por defecto han sido reestablecidos");
    }

    protected void updateGUIwithModel() {
        ProbReproIndividuoSlider.valueProperty().bindBidirectional(model.ProbReproIndividuoProperty());
        ProbClonIndividuoSlider.valueProperty().bindBidirectional(model.ProbClonIndividuoProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);

        valueFactory.setValue(3);
        TurnosVidaInicialSpinner.setValueFactory(valueFactory);

        if (model != null) {
            TurnosVidaInicialSpinner.getValueFactory().valueProperty().bindBidirectional(model.TurnosVidaInicialesProperty());
            this.updateGUIwithModel();
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
}