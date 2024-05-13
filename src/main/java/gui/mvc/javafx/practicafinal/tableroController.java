package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import es.uah.matcomp.mp.simulaciondevida.simuladorDeVida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class tableroController implements Initializable {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    private DataModel model;
    private simuladorDeVida juegoActual;

    @FXML
    private Label turnoLabel = new Label();

    @FXML
    ImageView iconoConfiguracion = new ImageView();
    @FXML
    Polygon trianguloPlay1 = new Polygon();
    @FXML
    Polygon trianguloPlay2 = new Polygon();

    @FXML
    Polygon trianguloPlay3 = new Polygon();

    @FXML
    Rectangle rectanguloPausa1 = new Rectangle();
    @FXML
    Rectangle rectanguloPausa2 = new Rectangle();



    public tableroController () {}
    public tableroController (DataModel model, simuladorDeVida juegoActual) {
        this.juegoActual = juegoActual;
        this.model = model;
    }
    @FXML
    protected void onBotonPausaClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        model.setPausado(true);
    }

    @FXML
    protected void onBotonReanudarClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        model.setPausado(false);
        avanzarJuego(event, false, casilla00);
    }

    @FXML
    protected void onBotonPasarTurnoClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        if (model.isPausado()) {
            avanzarJuego(event, true, casilla00);
        }
    }

    private void avanzarJuego (ActionEvent event, boolean unTurno, casillaTablero casilla00) {
        if (Window.getWindows().size() < 3) {
            if (Window.getWindows().size() > 1) {
                Stage ventanaCasilla = (Stage) Window.getWindows().get(1);
                ventanaCasilla.close();
            }
            tablero tableroActual = casilla00.getTablero();
            simuladorDeVida juegoActual = new simuladorDeVida(model, tableroActual);
            turnoLabel.textProperty().bind(model.getTurnoProperty().asString("Turno: %d"));
            juegoActual.comenzar(unTurno);
        }
    }

    @FXML
    protected void onBotonConfiguracionClick (ActionEvent event) throws IOException{
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        model.setPausado(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuConfiguracionPausa-vista.fxml"));
        Parent root = loader.load();
        loader.setController(new menuConfiguracionController(model));

        Stage stage = new Stage();

        Stage ventanaTablero = ((Stage) Window.getWindows().getFirst());
        stage.initOwner(ventanaTablero);
        stage.setResizable(false);

        if (Window.getWindows().size() > 1) {
            Stage ventanaCasila = (Stage) Window.getWindows().get(1);
            ventanaCasila.close();
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBotonCerrarClick () {
        ((Stage) Window.getWindows().getFirst()).close();
    }

    @FXML
    protected void onBotonPantallaCompletaClick () {
        Stage pantalla = ((Stage) Window.getWindows().getFirst());
        if (pantalla.isFullScreen()) {
            pantalla.setFullScreen(false);
        } else {
            pantalla.setFullScreen(true);
        }
    }

    protected void mostrarElementosCasilla (casillaTablero casilla) {
        try {
            if (model.isPausado()) {
                casillaController casillaController = new casillaController(model, casilla);

                Scene scene = new Scene(casillaController.getRoot());
                Stage stage = new Stage();
                stage.setResizable(false);

                Stage ventanaTablero = ((Stage) Window.getWindows().getFirst());
                stage.initOwner(ventanaTablero);

                stage.setScene(scene);

                int numeroVentanasAbiertas = 0;
                for (Window window : Window.getWindows()) {
                    numeroVentanasAbiertas++;
                }
                if (numeroVentanasAbiertas > 1) {
                    Stage ventanaPreviaCasilla = ((Stage) Window.getWindows().get(1));
                    String textoLabelVentana = ((Label) ((GridPane) ((VBox) ventanaPreviaCasilla.getScene().getRoot()).getChildren().getFirst()).getChildren().getFirst()).getText();
                    log.debug("Se ha cerrado la ventana de la casilla " + textoLabelVentana.charAt(8) + ", " + textoLabelVentana.charAt(11));
                    ventanaPreviaCasilla.close();
                }


                Bounds limitesCasilla = casilla.getBoundsInLocal();
                Bounds limitesEscenaCasilla = casilla.localToScene(limitesCasilla);
                Screen pantalla = Screen.getPrimary();
                Rectangle2D limitesPantalla = pantalla.getBounds();


                if (limitesEscenaCasilla.getMinX() < limitesPantalla.getWidth() / 2) {
                    stage.setX(limitesEscenaCasilla.getMinX());
                } else {
                    stage.setX(limitesEscenaCasilla.getMinX() - 220 - casilla.getPrefWidth());
                }
                if (limitesEscenaCasilla.getMinY() < limitesPantalla.getHeight() / 2) {
                    stage.setY(limitesEscenaCasilla.getMinY() - 20);
                } else {
                    stage.setY(limitesEscenaCasilla.getMinY() - 240);
                }

                stage.show();
                log.debug("Se ha pulsado la casilla" + casilla);
            }
        } catch (IOException e) {
            log.error("No se ha encontrado la vista de elementosCasilla");
        }
    }

    protected void crearTablero (tablero tablero) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tablero-vista.fxml"));
        GridPane gridTablero = this.crearGridTablero(tablero, root);

        ((AnchorPane) root.getChildrenUnmodifiable().get(1)).getChildren().add(gridTablero);
        AnchorPane.setTopAnchor(gridTablero, 0.0);
        AnchorPane.setRightAnchor(gridTablero, 0.0);
        AnchorPane.setBottomAnchor(gridTablero, 0.0);
        AnchorPane.setLeftAnchor(gridTablero, 0.0);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private GridPane crearGridTablero(tablero tablero, Parent root) {
        int casillasN = tablero.getNumeroCasillasN();
        int casillasM = tablero.getNumeroCasillasM();
        GridPane gridTablero = new GridPane();
        gridTablero.setHgap(1);
        gridTablero.setVgap(1);
        for (int i=0; i != casillasN; i++) {
            for (int j=0; j != casillasM; j++) {
                casillaTablero casilla = tablero.getCasilla(i, j);
                casilla.setPrefHeight(((AnchorPane) root.getChildrenUnmodifiable().get(1)).getPrefHeight()/casillasM);
                casilla.setPrefWidth(((AnchorPane) root.getChildrenUnmodifiable().get(1)).getPrefWidth()/casillasN);
                ((Button) casilla.getChildren().getFirst()).setOnAction(_ ->this.mostrarElementosCasilla(casilla));
                gridTablero.add(casilla, i, j);
            }
        }
        return gridTablero;
    }

    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public simuladorDeVida getJuegoActual() {
        return juegoActual;
    }

    public void setJuegoActual(simuladorDeVida juegoActual) {
        this.juegoActual = juegoActual;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iconoConfiguracion.setMouseTransparent(true);
        trianguloPlay1.setMouseTransparent(true);
        trianguloPlay2.setMouseTransparent(true);
        trianguloPlay3.setMouseTransparent(true);
        rectanguloPausa1.setMouseTransparent(true);
        rectanguloPausa2.setMouseTransparent(true);
    }
}