package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAvanzado;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoNormal;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ResourceBundle;

public class casillaController implements Initializable {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);
    private configuracionDataModel model;
    casillaTablero casillaActual;
    private boolean isListenerEnabled = true;
    @FXML
    private VBox individuosVBox = new VBox();
    @FXML
    private VBox recursosVBox = new VBox();
    @FXML
    private ChoiceBox<String> individuosAñadirBox = new ChoiceBox<>();
    private String[] tiposIndividuos = {"+ Básico", "+ Normal", "+ Avanzado"};
    @FXML
    private ChoiceBox<String> recursosAñadirBox = new ChoiceBox<>();
    private String[] tiposRecursos = {"+ Agua", "+ Comida", "+ Montaña", "+ Tesoro", "+ Biblioteca", "+ Pozo"};
    @FXML
    Label alertaCasillaLabel = new Label();

    public casillaController() {}

    public casillaController(configuracionDataModel model, casillaTablero casillaActual) {
        this.model = model;
        this.casillaActual = casillaActual;
    }


    public void añadirIndividuo () {
        try {
            isListenerEnabled = false;
            if (casillaActual.getIndividuos().getNumeroElementos() >= 3) {
                alertaCasillaLabel.setText("¡Ya hay 3 individuos!");
                PauseTransition pausa = new PauseTransition(Duration.millis(2500));
                pausa.setOnFinished(_ -> alertaCasillaLabel.setText("Casilla " + casillaActual.getPosicionX() + ", " + casillaActual.getPosicionY()));
                pausa.play();
                log.debug("Se ha intentado crear un individuo cuando ya había 3 en la casilla");
            } else {
                switch (individuosAñadirBox.getValue()) {
                    case "+ Básico":
                        añadirIndividuoAux(individuoBasico.class);
                        break;
                    case "+ Normal":
                        añadirIndividuoAux(individuoNormal.class);
                        break;
                    case "+ Avanzado":
                        añadirIndividuoAux(individuoAvanzado.class);
                        break;
                    default:
                        log.error("Se ha intentado añadir un tipo de individuo no esperado");
                }
            }
            individuosAñadirBox.setValue("Individuo");
            individuosAñadirBox.getSelectionModel().clearSelection();
            isListenerEnabled = true;
        } catch (Exception e) {
            log.warn("Se ha detectado una excepcion inesperada al añadir un individuo");
        }
    }

    private <T extends individuo> void añadirIndividuoAux (Class<T> individuoClase) {
        try {
            Constructor<T> constructor = individuoClase.getConstructor(int.class, int.class, int.class, float.class, float.class);

            int id;
            if (model.getIndividuos().isVacia()) {
                id = 1;
            } else {
                id = model.getIndividuos().getUltimo().getData().getId() + 1;
            }
            T individuo = constructor.newInstance(
                    id, model.getTurno(),
                    model.getTurnosVidaIniciales(), model.getProbReproIndividuo(), model.getProbClonIndividuo());

            individuo.setPosicion(casillaActual.getPosicion());

            model.getIndividuos().add(individuo);
            model.setIndividuos(model.getIndividuos());

            casillaActual.addIndividuo(individuo);

            HBox cajaIndividuo = FXMLLoader.load(getClass().getResource("elementoCasillaBox-vista.fxml"));

            Label labelIndividuo = (Label) cajaIndividuo.getChildren().getFirst();
            Font font = new Font("Bookman Old Style",12);
            labelIndividuo.setFont(font);
            String tipoIndividuo = individuoClase.getSimpleName().replace("individuo","");
            labelIndividuo.textProperty().bind(individuo.getTiempoDeVidaProperty().asString(
                    tipoIndividuo + ": Vida: %d Id: " + id + " Gen: " + individuo.getGeneracion()));

            Button botonQuitar = (Button) ((AnchorPane) cajaIndividuo.getChildren().get(1)).getChildren().getFirst();

            botonQuitar.setOnAction(this::eliminarIndividuo);

            individuosVBox.getChildren().add(cajaIndividuo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("No se ha podido crear una instancia del tipo de individuo pedido");
        }
    }

    public void eliminarIndividuo (ActionEvent event) {
        for (int i=0; i != model.getIndividuos().getNumeroElementos(); i++) {
            String labelIndividuoText = ((Label) ((HBox) ((Button) event.getSource()).getParent().getParent()).getChildren().getFirst()).getText();
            int indexOfId = labelIndividuoText.indexOf("Id:") + 4;
            if (model.getIndividuos().getElemento(i).getData().getId() == (int) labelIndividuoText.charAt(indexOfId)) {
                model.getIndividuos().del(i);
            }
        }
        individuosVBox.getChildren().remove(((Button) event.getSource()).getParent().getParent());
    }

    public void añadirRecurso () {
        try {
            isListenerEnabled = false;
            if (casillaActual.getRecursos().getNumeroElementos() >= 3) {
                alertaCasillaLabel.setText("¡Ya hay 3 recursos!");
                PauseTransition pausa = new PauseTransition(Duration.millis(2500));
                pausa.setOnFinished(_ -> alertaCasillaLabel.setText("Casilla " + casillaActual.getPosicionX() + ", " + casillaActual.getPosicionY()));
                pausa.play();
                log.debug("Se ha intentado crear un recurso cuando ya había 3 en la casilla");
            } else {
                switch (recursosAñadirBox.getValue()) {
                    case "+ Agua":
                        añadirRecursoAux(agua.class);
                        break;
                    case "+ Comida":
                        añadirRecursoAux(comida.class);
                        break;
                    case "+ Montaña":
                        añadirRecursoAux(montaña.class);
                        break;
                    case "+ Tesoro":
                        añadirRecursoAux(tesoro.class);
                        break;
                    case "+ Biblioteca":
                        añadirRecursoAux(biblioteca.class);
                        break;
                    case "+ Pozo":
                        añadirRecursoAux(pozo.class);
                        break;
                    default:
                        log.error("Se ha detectado una excepcion inesperada al añadir un individuo");
                }
            }
            recursosAñadirBox.setValue("Recurso");
            recursosAñadirBox.getSelectionModel().clearSelection();
            isListenerEnabled = true;
        } catch (Exception e) {
            log.warn("Se ha detectado una excepcion inesperada al añadir un recurso");
        }
    }

    private <T extends recurso> void añadirRecursoAux (Class<T> recursoClase) {
        try {
            Constructor<T> constructor = recursoClase.getConstructor(int.class, int.class);

            int id;
            if (model.getRecursos().isVacia()) {
                id = 1;
            } else {
                id = model.getRecursos().getUltimo().getData().getId() + 1;
            }
            T recurso = constructor.newInstance(id, model.getTurnosInicialesRecurso());

            recurso.setPosicion(casillaActual.getPosicion());

            model.getRecursos().add(recurso);
            model.setRecursos(model.getRecursos());

            casillaActual.addRecurso(recurso);

            HBox cajaRecurso = FXMLLoader.load(getClass().getResource("elementoCasillaBox-vista.fxml"));

            Label labelRecurso = (Label) cajaRecurso.getChildren().getFirst();
            Font font = new Font("Bookman Old Style",12);
            labelRecurso.setFont(font);
            String tipoRecurso = Character.toUpperCase(recurso.getClass().getSimpleName().charAt(0)) + recurso.getClass().getSimpleName().substring(1);
            labelRecurso.textProperty().bind(recurso.getTiempoDeAparicionProperty().asString(
                    tipoRecurso + ": Turnos restantes: %d Id: " + id));

            Button botonQuitar = (Button) ((AnchorPane) cajaRecurso.getChildren().get(1)).getChildren().getFirst();

            botonQuitar.setOnAction(this::eliminarRecurso);

            recursosVBox.getChildren().add(cajaRecurso);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("No se ha podido crear una instancia del tipo de recurso pedido");
        }
    }

    public void eliminarRecurso (ActionEvent event) {
        for (int i=0; i != model.getRecursos().getNumeroElementos(); i++) {
            String labelRecursoText = ((Label) ((HBox) ((Button) event.getSource()).getParent().getParent()).getChildren().getFirst()).getText();
            int indexOfId = labelRecursoText.indexOf("Id:") + 4;
            if (model.getRecursos().getElemento(i).getData().getId() == (int) labelRecursoText.charAt(indexOfId)) {
                model.getRecursos().del(i);
            }
        }
        recursosVBox.getChildren().remove(((Button) event.getSource()).getParent().getParent());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            alertaCasillaLabel.setText("Casilla " + casillaActual.getPosicionX() + ", " + casillaActual.getPosicionY());

            individuosAñadirBox.getItems().setAll(tiposIndividuos);
            recursosAñadirBox.getItems().setAll(tiposRecursos);
            individuosAñadirBox.setStyle("-fx-font-family: 'Bookman Old Style'; -fx-font-size: 12px;");
            recursosAñadirBox.setStyle("-fx-font-family: 'Bookman Old Style'; -fx-font-size: 12px;");
            individuosAñadirBox.setValue("Individuo");
            recursosAñadirBox.setValue("Recurso");

            individuosAñadirBox.valueProperty().addListener((_, _, _) -> {
                if (isListenerEnabled) {
                    this.añadirIndividuo();
                }
            });
            recursosAñadirBox.valueProperty().addListener((_, _, _) -> {
                if (isListenerEnabled) {
                    this.añadirRecurso();
                }
            });

        } catch (Exception e) {
            log.trace("Excepcion cazada sin accion");
        }
    }
}
