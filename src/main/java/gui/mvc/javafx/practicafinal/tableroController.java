package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import es.uah.matcomp.mp.simulaciondevida.simuladorDeVida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class tableroController {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    private simuladorDeVida juegoActual;

    @FXML
    private Label Titulo;

    @FXML
    protected void mostrarElementosCasilla (casillaTablero casilla) {
        log.info("Se ha pulsado la casilla" + casilla);
    }

    protected void empezarNuevoJuego (configuracionDataModelProperties model) throws IOException {
        juegoActual = new simuladorDeVida(model);

        GridPane gridTablero = this.crearGridTablero(model.FilasTableroProperty().getValue(), model.ColumnasTableroProperty().getValue());

        Parent root = FXMLLoader.load(getClass().getResource("tablero-vista.fxml"));
        ((AnchorPane) root.getChildrenUnmodifiable().get(1)).getChildren().add(gridTablero);
        AnchorPane.setTopAnchor(gridTablero, 0.0);
        AnchorPane.setRightAnchor(gridTablero, 0.0);
        AnchorPane.setBottomAnchor(gridTablero, 0.0);
        AnchorPane.setLeftAnchor(gridTablero, 0.0);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane crearGridTablero(int casillasN, int casillasM) {
        GridPane gridTablero = new GridPane();
        gridTablero.setHgap(5);
        gridTablero.setVgap(5);
        for (int i=0; i != casillasN; i++) {
            for (int j=0; j != casillasM; j++) {
                casillaTablero casilla = new casillaTablero();
                casilla.setText("Casilla " + i + "," + j);
                casilla.setOnAction(_ -> mostrarElementosCasilla(casilla));
                gridTablero.add(casilla, i, j);
            }
        }
        return gridTablero;
    }
}