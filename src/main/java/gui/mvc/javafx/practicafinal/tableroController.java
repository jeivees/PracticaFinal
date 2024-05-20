package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import es.uah.matcomp.mp.simulaciondevida.control.simuladorDeVida;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.ArbolBinario;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.nodoBST;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;

public class tableroController {
    private static final Logger log = LogManager.getLogger();

    private DataModel model;
    private simuladorDeVida juegoActual;

    @FXML
    private Label turnoLabel = new Label();

    @FXML
    private HBox paneGanadores = new HBox();


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
        if (getModel().isPausado()) {
            model.setPausado(false);
            avanzarJuego(false, casilla00);
        }
    }

    @FXML
    protected void onBotonPasarTurnoClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        if (model.isPausado()) {
            avanzarJuego(true, casilla00);
        }
    }

    private void avanzarJuego (boolean unTurno, casillaTablero casilla00) {
        if (Window.getWindows().size() < 3) {
            if (Window.getWindows().size() > 1) {
                Stage ventanaCasilla = (Stage) Window.getWindows().get(1);
                ventanaCasilla.close();
            }
            tablero tableroActual = casilla00.getTablero();
            simuladorDeVida juegoActual = new simuladorDeVida(model, tableroActual);
            turnoLabel.textProperty().bind(juegoActual.getBucle().getTurnoProperty().asString("Turno: %d"));
            juegoActual.getBucle().updateTurnoProperty();
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
        menuConfiguracionController controller = loader.getController();
        controller.setControllerValues(model);

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
        pantalla.setFullScreen(!pantalla.isFullScreen());
    }

    @FXML
    protected void onBotonMinimizarClick () {
        ((Stage) Window.getWindows().getFirst()).setIconified(true);
    }

    @FXML
    protected void onBotonMenuPrincipalClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        if (!getModel().isGuardado() && model.isPausado()) {
            if (Window.getWindows().size() > 1) ((Stage) Window.getWindows().get(1)).close();
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.initOwner(Stage.getWindows().getFirst());
            confirmacion.setTitle("Volver al menú principal");
            confirmacion.setHeaderText("Estás a punto de volver al menú principal, perderás el progreso");
            confirmacion.setContentText("¿Quieres guardar la partida antes de salir?");

            ButtonType botonGuardar = new ButtonType("Guardar");
            ButtonType botonNoGuardar = new ButtonType("No guardar");
            ButtonType botonCancelar = new ButtonType("Cancelar");
            confirmacion.getButtonTypes().setAll(botonGuardar, botonNoGuardar, botonCancelar);

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == botonGuardar) {
                    guardarPartida(event);
                    volverAlMenuPrincipal(event);
                } else if (respuesta == botonNoGuardar) {
                    volverAlMenuPrincipal(event);
                } else {
                    confirmacion.close();
                }
            });
        } else {
            volverAlMenuPrincipal(event);
        }
    }

    private void volverAlMenuPrincipal (ActionEvent event) {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("menuPrincipal-vista.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Simulador de Vida");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        } catch (IOException e) {
            log.error("No se ha encontrado la vista del menú principal");
        }
    }

    @FXML
    protected void onBotonGuardarPartidaClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        guardarPartida(event);
    }

    @FXML
    protected void onBotonGuardarComoClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        guardarComo(event);
    }

    protected void guardarPartida (ActionEvent event) {
        if (model.isPausado()) {
            if (model.getNombreArchivo() == null) {
                guardarComo(event);
            } else {
                model.guardar(model.getNombreArchivo());
            }
        }
    }

    protected void guardarComo (ActionEvent event) {
        TextInputDialog getArchivoNombre = new TextInputDialog();
        getArchivoNombre.initOwner(Stage.getWindows().getFirst());
        getArchivoNombre.setTitle("Guardar partida");
        getArchivoNombre.setHeaderText("¿Cómo quieres llamar a tu partida?");
        getArchivoNombre.setContentText("Guardar como:");

        ButtonType botonGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        getArchivoNombre.getDialogPane().getButtonTypes().setAll(botonGuardar, botonCancelar);

        String nombreArchivo = getArchivoNombre.showAndWait().get();

        Label labelTurno = (Label) ((HBox) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(2)).getChildren().getFirst();
        String labelString = labelTurno.getText();
        int turnoActual = Integer.parseInt(labelString.replace("Turno: ", ""));
        model.setTurno(turnoActual);

        if (model.getNombreArchivo() != null) {
            File archivoAntiguo = new File("archivosDePartida/" + model.getNombreArchivo() + ".json");
            archivoAntiguo.delete();
        }

        model.setNombreArchivo(nombreArchivo);
        model.guardar(nombreArchivo);
    }

    @FXML
    protected void onBotonFinalizarPartidaClick (ActionEvent event) {
        casillaTablero casilla00 = ((casillaTablero) ((GridPane) ((AnchorPane) ((Node) event.getSource()).getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable().getFirst()).getChildren().getFirst());
        model = casilla00.getModel();
        finalizarPartida(event);
    }

    public void finalizarPartida (ActionEvent event) {
        juegoActual = new simuladorDeVida(model, true);
        juegoActual.crearInfoPartida();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("finalizarPartida-vista.fxml"));
            Parent root = fxmlLoader.load();
            paneGanadores = (HBox) ((AnchorPane) ((ScrollPane) ((VBox) root).getChildren().get(2)).getContent()).getChildren().getFirst();

            HBox.setHgrow(paneGanadores, Priority.ALWAYS);
            AnchorPane.setTopAnchor(paneGanadores, 0.0);
            AnchorPane.setBottomAnchor(paneGanadores, 0.0);
            AnchorPane.setLeftAnchor(paneGanadores, 0.0);
            AnchorPane.setRightAnchor(paneGanadores, 0.0);

            HashMap<individuo, ArbolBinario<individuo>> arbolesGenealogicos = juegoActual.getArbolesGenealogicos();

            int numeroGanadores = model.getIndividuos().getNumeroElementos();
            for (int i = 0; i != numeroGanadores; i ++) {
                individuo individuoActual = model.getIndividuos().getElemento(i).getData();
                ArbolBinario<individuo> arbolActual = arbolesGenealogicos.get(individuoActual);

                TreeView<individuo> vistaGanadores = new TreeView<>();

                TreeItem<individuo> raiz = new TreeItem<>();
                raiz.getChildren().add(crearArbolVista(arbolActual));
                vistaGanadores.setRoot(raiz);
                vistaGanadores.setShowRoot(false);
                vistaGanadores.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                vistaGanadores.setCellFactory(_ -> new TreeCell<>() {
                    @Override
                    protected void updateItem(individuo individuo, boolean empty) {
                        super.updateItem(individuo, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if (individuo != null) {
                                setText(STR."Individuo \{individuo.getId()}");
                                setFont(new Font("Bookman Old Style", 18));
                            }
                        }
                    }
                });

                paneGanadores.getChildren().add(vistaGanadores);
                HBox.setHgrow(vistaGanadores, Priority.ALWAYS);
            }


            while (Window.getWindows().size() > 1) {
                ((Stage) Window.getWindows().get(1)).close();
            };
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setIconified(false);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            log.error("No se ha encontrado la vista del menú principal");
        }
    }

    private TreeItem<individuo> crearArbolVista (ArbolBinario<individuo> arbol) {
        TreeItem<individuo> itemRaiz = new TreeItem<>(arbol.getRaiz().getDato());
        if (arbol.getAltura() > 1) {
            crearArbolVistaAux(arbol.getRaiz().getDerecha(), itemRaiz);
            crearArbolVistaAux(arbol.getRaiz().getIzquierda(), itemRaiz);
        }
        return itemRaiz;
    }

    private void crearArbolVistaAux (nodoBST<individuo> nodo, TreeItem<individuo> itemRaiz) {
        TreeItem<individuo> item = new TreeItem<>(nodo.getDato());
        itemRaiz.getChildren().add(item);

        if (nodo.getDerecha() != null & nodo.getIzquierda() != null) {
            crearArbolVistaAux(nodo.getDerecha(), item);
            crearArbolVistaAux(nodo.getIzquierda(), item);
        }
    }

    @FXML
    protected void onBotonSalirClick (ActionEvent event) {
        volverAlMenuPrincipal(event);
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

        turnoLabel = (Label) ((HBox) root.getChildrenUnmodifiable().get(2)).getChildren().getFirst();
        juegoActual.getBucle().updateTurnoProperty();
        turnoLabel.textProperty().bind(juegoActual.getBucle().getTurnoProperty().asString("Turno: %d"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        stage.show();

        model.setPausado(true);
        int numeroIndividuos = model.getIndividuos().getNumeroElementos();
        for (int k=0; k != numeroIndividuos; k++) {
            individuo individuoActual = model.getIndividuos().getElemento(k).getData();
            casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
            casillaActual.addIndividuo(individuoActual, false);
            casillaActual.getIndividuos().add(individuoActual);
        }
        int numeroRecursos = model.getRecursos().getNumeroElementos();
        for (int k=0; k != numeroRecursos; k++) {
            recurso recursoActual = model.getRecursos().getElemento(k).getData();
            casillaTablero casillaActual = tablero.getCasilla(recursoActual.getPosicion());
            casillaActual.addRecurso(recursoActual, false);
            casillaActual.getRecursos().add(recursoActual);
        }
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
}