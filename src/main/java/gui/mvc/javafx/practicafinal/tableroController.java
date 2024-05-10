package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import es.uah.matcomp.mp.simulaciondevida.simuladorDeVida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    private configuracionDataModel model;
    private simuladorDeVida juegoActual;

    @FXML
    private Label turnoLabel = new Label();

    public tableroController () {}
    public tableroController (configuracionDataModel model) {
        this.model = model;
    }
    @FXML
    protected void onBotonPausaClick (ActionEvent event) {
        model = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst()).getModel();
        model.setPausado(true);
    }

    @FXML
    protected void onBotonReanudarClick (ActionEvent event) {
        model = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst()).getModel();
        model.setPausado(false);
        juegoActual = new simuladorDeVida(model);
        turnoLabel.textProperty().bind(model.getTurnoProperty().asString("Turno: %d"));
        juegoActual.comenzar();
    }

    @FXML
    protected void onBotonConfiguracionClick (ActionEvent event) throws IOException{
        menuConfiguracionController configC = new menuConfiguracionController(model);
        model.setPausado(true);
        configC.mostrarMenuConfiguracion();
    }

    protected void mostrarElementosCasilla (casillaTablero casilla) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("elementosCasilla-vista.fxml"));
            casillaController casillaController = new casillaController(model, casilla);
            loader.setController(casillaController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            log.info("Se ha pulsado la casilla" + casilla);
        } catch (IOException e) {
            log.error("No se ha encontrado la vista de elementosCasilla");
            e.printStackTrace();
        }
    }

    protected void crearTablero () throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tablero-vista.fxml"));
        GridPane gridTablero = this.crearGridTablero(model.getFilasTablero(), model.getColumnasTablero(), root);

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

    private GridPane crearGridTablero(int casillasN, int casillasM, Parent root) {
        GridPane gridTablero = new GridPane();
        gridTablero.setHgap(1);
        gridTablero.setVgap(1);
        for (int i=0; i != casillasN; i++) {
            for (int j=0; j != casillasM; j++) {
                casillaTablero casilla = new casillaTablero(i,j);
                casilla.setPrefHeight(((AnchorPane) root.getChildrenUnmodifiable().get(1)).getPrefHeight()/casillasM);
                casilla.setPrefWidth(((AnchorPane) root.getChildrenUnmodifiable().get(1)).getPrefWidth()/casillasN);
                casilla.setOnAction(_ ->this.mostrarElementosCasilla(casilla));
                if (i == 0 && j == 0) casilla.setModel(model);
                gridTablero.add(casilla, i, j);
            }
        }
        return gridTablero;
    }

    public configuracionDataModel getModel() {
        return model;
    }

    public void setModel(configuracionDataModel model) {
        this.model = model;
    }

    public simuladorDeVida getJuegoActual() {
        return juegoActual;
    }

    public void setJuegoActual(simuladorDeVida juegoActual) {
        this.juegoActual = juegoActual;
    }
}