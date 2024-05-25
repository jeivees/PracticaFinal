package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import excepciones.individuoNoExistenteException;
import excepciones.recursoNoExistenteException;
import gui.mvc.javafx.practicafinal.casillaController;
import gui.mvc.javafx.practicafinal.DataModel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class casillaTablero extends AnchorPane {
    private int posicionX;
    private int posicionY;
    private DataModel model;
     private tablero tablero;
    private ListaEnlazada<individuo> individuos = new ListaEnlazada<>();
    private ListaEnlazada<recurso> recursos = new ListaEnlazada<>();

    private Button botonCasilla = new Button();
    private GridPane gridElementos = new GridPane();

    private static final Logger log = LogManager.getLogger(casillaTablero.class);

    public casillaTablero (int x, int y, DataModel model, tablero tablero) {
        posicionX = x;
        posicionY = y;
        this.model = model;
        this.tablero = tablero;
        botonCasilla.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        AnchorPane.setTopAnchor(botonCasilla, 0.0);
        AnchorPane.setRightAnchor(botonCasilla, 0.0);
        AnchorPane.setBottomAnchor(botonCasilla, 0.0);
        AnchorPane.setLeftAnchor(botonCasilla, 0.0);
        AnchorPane.setTopAnchor(gridElementos, 0.0);
        AnchorPane.setRightAnchor(gridElementos, 0.0);
        AnchorPane.setBottomAnchor(gridElementos, 0.0);
        AnchorPane.setLeftAnchor(gridElementos, 0.0);
        gridElementos.setVgap(3);
        gridElementos.setHgap(8);
        gridElementos.setMouseTransparent(true);
        getChildren().setAll(botonCasilla, gridElementos);
    }

    public ListaEnlazada<individuo> getIndividuos() {
        return individuos;
    }

    public ListaEnlazada<recurso> getRecursos() {
        return recursos;
    }

    public void setIndividuos(ListaEnlazada<individuo> individuos) {
        this.individuos = individuos;
    }

    public void setRecursos(ListaEnlazada<recurso> recursos) {
        this.recursos = recursos;
    }
    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public void addIndividuo (individuo individuo, boolean nuevoIndividuo) {
        try {
            if (nuevoIndividuo) individuo.añadir(model, this);

            ImageView vistaIcono;
            switch (individuo.getClass().getSimpleName()) {
                case "individuoBasico":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/caveman.png").toExternalForm()));
                    break;
                case "individuoNormal":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/man.png").toExternalForm()));
                    break;
                case "individuoAvanzado":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/futuristic-man.png").toExternalForm()));
                    break;
                default:
                    throw new individuoNoExistenteException();
            }
            addIconoIndividuo(vistaIcono);
        } catch (individuoNoExistenteException e) {
            log.error("Se ha tratado de añadir el icono de un tipo de individuo no existente");
        }
    }

    private void addIconoIndividuo (ImageView vistaIcono) {
        vistaIcono.setPreserveRatio(true);
        vistaIcono.setFitHeight(((GridPane) this.getParent()).getHeight()/(((GridPane) this.getParent()).getRowCount() * 2));
        vistaIcono.setFitWidth(((GridPane) this.getParent()).getWidth()/(((GridPane) this.getParent()).getColumnCount() * 3));
        int numeroIconosIndividuo = 0;
        for (Node node : gridElementos.getChildren() ) {
            if (GridPane.getRowIndex(node) == 0) numeroIconosIndividuo++;
        }
        gridElementos.add(vistaIcono, numeroIconosIndividuo , 0);
    }

    public void delIndividuo (individuo individuo) {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            if (individuo == individuos.getElemento(i).getData()) {
                individuo.morir(model, this);
                resetVisual();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un individuo de una casilla en la que no está contenido");
    }

    public void addRecurso (recurso recurso, boolean nuevoRecurso) {
        try {
            if (nuevoRecurso) recurso.añadir(model, this);

            ImageView vistaIcono;
            switch (recurso.getClass().getSimpleName()) {
                case "agua":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/water-drop.png").toExternalForm()));
                    break;
                case "comida":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/apple.png").toExternalForm()));
                    break;
                case "montaña":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/mountain-range.png").toExternalForm()));
                    break;
                case "tesoro":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/treasure-chest.png").toExternalForm()));
                    break;
                case "biblioteca":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/books.png").toExternalForm()));
                    break;
                case "pozo":
                    vistaIcono = new ImageView(new Image(casillaController.class.getResource("images/elementos/water-well.png").toExternalForm()));
                    break;
                default:
                    throw new recursoNoExistenteException();
            }
            addIconoRecurso(vistaIcono);
        } catch (recursoNoExistenteException e) {
            log.error("Se ha tratado de añadir el icono de un tipo de recurso no existente");
        }
    }

    private void addIconoRecurso (ImageView vistaIcono) {
        vistaIcono.setPreserveRatio(true);
        vistaIcono.setFitWidth(((GridPane) this.getParent()).getWidth()/(((GridPane) this.getParent()).getColumnCount() * 3));
        vistaIcono.setFitHeight(((GridPane) this.getParent()).getHeight()/(((GridPane) this.getParent()).getRowCount() * 2));
        int numeroIconosRecurso = 0;
        for (Node node : gridElementos.getChildren() ) {
            if (GridPane.getRowIndex(node) == 1) numeroIconosRecurso++;
        }
        gridElementos.add(vistaIcono, numeroIconosRecurso, 1);
    }

    public void delRecurso (recurso recurso) {
        for (int i = 0; i != recursos.getNumeroElementos(); i++) {
            if (recurso == recursos.getElemento(i).getData()) {
                recurso.eliminar(model, this);
                resetVisual();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un recurso de una casilla en la que no está contenido");
    }

    public void resetVisual () {
        gridElementos.getChildren().clear();
        gridElementos.getColumnConstraints().clear();
        gridElementos.getRowConstraints().clear();
        for (int i=0; i != individuos.getNumeroElementos(); i++) {
            addIndividuo(individuos.getElemento(i).getData(), false);
        }
        for (int i=0; i != recursos.getNumeroElementos(); i++) {
            addRecurso(recursos.getElemento(i).getData(), false);
        }
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int getPosicionY) {
        this.posicionY = getPosicionY;
    }

    public int[] getPosicion () {
        return new int[]{posicionX, posicionY};
    }

    public tablero getTablero() {
        return tablero;
    }

    public void setTablero(tablero tablero) {
        this.tablero = tablero;
    }
}
