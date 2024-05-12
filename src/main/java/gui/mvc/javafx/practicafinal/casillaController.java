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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ResourceBundle;

public class casillaController implements Initializable {
    private static final Logger log = LogManager.getLogger();
    private configuracionDataModel model;
    casillaTablero casillaActual;

    private Parent root;
    private boolean isListenerEnabled = true;

    @FXML
    private VBox individuosVBox;
    @FXML
    private VBox recursosVBox;
    @FXML
    private ChoiceBox<String> individuosAñadirBox = new ChoiceBox<>();
    private String[] tiposIndividuos = {"+ Básico", "+ Normal", "+ Avanzado"};
    @FXML
    private ChoiceBox<String> recursosAñadirBox = new ChoiceBox<>();
    private String[] tiposRecursos = {"+ Agua", "+ Comida", "+ Montaña", "+ Tesoro", "+ Biblioteca", "+ Pozo"};
    @FXML
    Label alertaCasillaLabel = new Label();

    public casillaController() {}

    public <T extends individuo> casillaController(configuracionDataModel model, casillaTablero casillaActual) throws IOException {
        this.model = model;
        this.casillaActual = casillaActual;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("casilla-vista.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        individuosVBox = (VBox)((GridPane) ((VBox) root).getChildren().getFirst()).getChildren().get(1);
        recursosVBox = (VBox)((GridPane) ((VBox) root).getChildren().getFirst()).getChildren().get(3);
        this.root = root;

    if (!casillaActual.getIndividuos().isVacia()) {
            for (int i = 0; i != casillaActual.getIndividuos().getNumeroElementos(); i++) {
                Class<T> clase = casillaActual.getIndividuos().getElemento(i).getData().getTipo();
                añadirIndividuoAux(casillaActual.getIndividuos().getElemento(i).getData().getTipo(), false, casillaActual.getIndividuos().getElemento(i).getData());
            }
        }
        if (!casillaActual.getRecursos().isVacia()) {
            for (int i = 0; i != casillaActual.getRecursos().getNumeroElementos(); i++) {
                añadirRecursoAux(casillaActual.getRecursos().getElemento(i).getData().getTipo(), false,
                        casillaActual.getRecursos().getElemento(i).getData());
            }
        }
    }


    public void añadirIndividuo (String individuoTipo) {
        try {
            isListenerEnabled = false;
            if (casillaActual.getIndividuos().getNumeroElementos() >= 3) {
                alertaCasillaLabel.setText("¡Ya hay 3 individuos!");
                PauseTransition pausa = new PauseTransition(Duration.millis(2500));
                pausa.setOnFinished(_ -> alertaCasillaLabel.setText("Casilla " + casillaActual.getPosicionX() + ", " + casillaActual.getPosicionY()));
                pausa.play();
                log.debug("Se ha intentado crear un individuo cuando ya había 3 en la casilla");
            } else {
                switch (individuoTipo) {
                    case "+ Básico":
                        añadirIndividuoAux(individuoBasico.class, true, null);
                        break;
                    case "+ Normal":
                        añadirIndividuoAux(individuoNormal.class, true, null);
                        break;
                    case "+ Avanzado":
                        añadirIndividuoAux(individuoAvanzado.class, true, null);
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

    private <T extends individuo> void añadirIndividuoAux (Class<T> individuoClase, boolean nuevoIndividuo, T individuoToAdd) {
        try {
            T individuo;
            if (nuevoIndividuo) {
                Constructor<T> constructor = individuoClase.getConstructor(int.class, int.class, int.class, float.class, float.class);

                int id;
                if (model.getHistorialIndividuos().isVacia()) {
                    id = 1;
                } else {
                    id = model.getHistorialIndividuos().getUltimo().getData().getId() + 1;
                }
                individuo = constructor.newInstance(
                        id, model.getTurno(),
                        model.getTurnosVidaIniciales(), model.getProbReproIndividuo(), model.getProbClonIndividuo());

                casillaActual.addIndividuo(individuo, true);
            } else {
                individuo = individuoToAdd;
            }

            HBox cajaIndividuo = FXMLLoader.load(getClass().getResource("elementoCasillaBox-vista.fxml"));

            Label labelIndividuo = (Label) cajaIndividuo.getChildren().getFirst();
            Font font = new Font("Bookman Old Style",12);
            labelIndividuo.setFont(font);
            String tipoIndividuo = individuoClase.getSimpleName().replace("individuo","");
            labelIndividuo.textProperty().bind(individuo.getTiempoDeVidaProperty().asString(
                    tipoIndividuo + ": Vida: %d Id: " + individuo.getId() + " Gen: " + individuo.getGeneracion()));

            Button botonQuitar = (Button) ((AnchorPane) cajaIndividuo.getChildren().get(1)).getChildren().getFirst();

            botonQuitar.setOnAction(this::eliminarIndividuo);

            individuosVBox.getChildren().add(cajaIndividuo);
        } catch (Exception e) {
            log.error("No se ha podido crear una instancia del tipo de individuo pedido");
        }
    }

    public void eliminarIndividuo (ActionEvent event) {
        int i = 0;
        boolean yaEliminado = false;
        while (i != model.getIndividuos().getNumeroElementos() && !yaEliminado) {
            String labelIndividuoText = ((Label) ((HBox) ((Button) event.getSource()).getParent().getParent()).getChildren().getFirst()).getText();
            int indexOfIdLabel = labelIndividuoText.indexOf("Id:") + 4;
            int idLabel = (int) labelIndividuoText.charAt(indexOfIdLabel) - '0';

            int idIndividuo = model.getIndividuos().getElemento(i).getData().getId();

            if (idIndividuo == idLabel) {
                casillaActual.delIndividuo(model.getIndividuos().getElemento(i).getData());
                individuosVBox.getChildren().remove(((Button) event.getSource()).getParent().getParent());
                yaEliminado = true;
            }
            i++;
        }
    }

    public void añadirRecurso (String recursoTipo) {
        try {
            isListenerEnabled = false;
            if (casillaActual.getRecursos().getNumeroElementos() >= 3) {
                alertaCasillaLabel.setText("¡Ya hay 3 recursos!");
                PauseTransition pausa = new PauseTransition(Duration.millis(2500));
                pausa.setOnFinished(_ -> alertaCasillaLabel.setText("Casilla " + casillaActual.getPosicionX() + ", " + casillaActual.getPosicionY()));
                pausa.play();
                log.debug("Se ha intentado crear un recurso cuando ya había 3 en la casilla");
            } else {
                switch (recursoTipo) {
                    case "+ Agua":
                        añadirRecursoAux(agua.class, true, null);
                        break;
                    case "+ Comida":
                        añadirRecursoAux(comida.class, true, null);
                        break;
                    case "+ Montaña":
                        añadirRecursoAux(montaña.class, true, null);
                        break;
                    case "+ Tesoro":
                        añadirRecursoAux(tesoro.class, true, null);
                        break;
                    case "+ Biblioteca":
                        añadirRecursoAux(biblioteca.class, true, null);
                        break;
                    case "+ Pozo":
                        añadirRecursoAux(pozo.class, true, null);
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

    private <T extends recurso> void añadirRecursoAux (Class<T> recursoClase, boolean nuevoRecurso, T recursoToAdd) {
        try {
            T recurso;
            if (nuevoRecurso) {
                Constructor<T> constructor = recursoClase.getConstructor(int.class, int.class);

                int id;
                if (model.getHistorialRecursos().isVacia()) {
                    id = 1;
                } else {
                    id = model.getHistorialRecursos().getUltimo().getData().getId() + 1;
                }
                recurso = constructor.newInstance(id, model.getTurnosInicialesRecurso());

                casillaActual.addRecurso(recurso, true);
            } else {
                recurso = recursoToAdd;
            }

            HBox cajaRecurso = FXMLLoader.load(getClass().getResource("elementoCasillaBox-vista.fxml"));

            Label labelRecurso = (Label) cajaRecurso.getChildren().getFirst();
            Font font = new Font("Bookman Old Style",12);
            labelRecurso.setFont(font);
            String tipoRecurso = Character.toUpperCase(recursoClase.getSimpleName().charAt(0)) + recursoClase.getSimpleName().substring(1);
            labelRecurso.textProperty().bind(recurso.getTiempoDeAparicionProperty().asString(
                    tipoRecurso + ": Vida: %d Id: " + recurso.getId()));

            Button botonQuitar = (Button) ((AnchorPane) cajaRecurso.getChildren().get(1)).getChildren().getFirst();

            botonQuitar.setOnAction(this::eliminarRecurso);

            recursosVBox.getChildren().add(cajaRecurso);
        } catch (Exception e) {
            log.error("No se ha podido crear una instancia del tipo de recurso pedido");
        }
    }

    public void eliminarRecurso (ActionEvent event) {
        String labelRecursoText = ((Label) ((HBox) ((Button) event.getSource()).getParent().getParent()).getChildren().getFirst()).getText();
        int indexOfId = labelRecursoText.indexOf("Id:") + 4;
        int id = labelRecursoText.charAt(indexOfId) - '0';
        int i = 0;
        boolean yaEliminado = false;
        while (i != model.getRecursos().getNumeroElementos() && !yaEliminado) {
            int idRecurso = model.getRecursos().getElemento(i).getData().getId();
            if (idRecurso == id) {
                casillaActual.delRecurso(model.getRecursos().getElemento(i).getData());
                recursosVBox.getChildren().remove(((Button) event.getSource()).getParent().getParent());
                yaEliminado = true;
            }
            i++;
        }
    }

    public Parent getRoot () {
        return root;
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

            individuosAñadirBox.valueProperty().addListener((_, _, newValue) -> {
                if (isListenerEnabled) {
                    this.añadirIndividuo(newValue);
                }
            });
            recursosAñadirBox.valueProperty().addListener((_, _, newValue) -> {
                if (isListenerEnabled) {
                    this.añadirRecurso(newValue);
                }
            });

        } catch (Exception e) {
            log.trace("Excepcion cazada sin accion");
        }
    }
}
