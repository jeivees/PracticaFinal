package gui.mvc.javafx.practicafinal;

import excepciones.reinicioPestañaInesperadaException;
import javafx.beans.property.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class configuracionDataModelProperties {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    //Modelo de datos original
    protected configuracionDataModel original;


    // propiedades tablero
    private ObjectProperty<Integer> FilasTablero = new SimpleIntegerProperty().asObject();
    private ObjectProperty<Integer> ColumnasTablero = new SimpleIntegerProperty().asObject();



    // propiedades individuos
    private ObjectProperty<Integer> TurnosVidaIniciales = new SimpleIntegerProperty().asObject();
    private IntegerProperty ProbReproIndividuo = new SimpleIntegerProperty();
    private IntegerProperty ProbClonIndividuo = new SimpleIntegerProperty();
    private IntegerProperty ProbMejoraToNormal = new SimpleIntegerProperty();
    private IntegerProperty ProbMejoraToAvanzado = new SimpleIntegerProperty();

    // propiedades recursos
    private IntegerProperty ProbAparRecurso = new SimpleIntegerProperty();
    private ObjectProperty<Integer> TurnosInicialesRecurso = new SimpleIntegerProperty().asObject();
    private IntegerProperty ProbAparAgua = new SimpleIntegerProperty();
    private IntegerProperty ProbAparComida = new SimpleIntegerProperty();
    private IntegerProperty ProbAparMontaña = new SimpleIntegerProperty();
    private IntegerProperty ProbAparTesoro = new SimpleIntegerProperty();
    private IntegerProperty ProbAparBiblioteca = new SimpleIntegerProperty();
    private IntegerProperty ProbAparPozo = new SimpleIntegerProperty();
    private ObjectProperty<Integer> IncrementoTurnosAgua = new SimpleIntegerProperty().asObject();
    private ObjectProperty<Integer> IncrementoTurnosComida = new SimpleIntegerProperty().asObject();
    private ObjectProperty<Integer> IncrementoTurnosMontaña = new SimpleIntegerProperty().asObject();
    private IntegerProperty IncrementoProbRepro = new SimpleIntegerProperty();
    private IntegerProperty IncrementoProbClon = new SimpleIntegerProperty();


    public configuracionDataModelProperties(configuracionDataModel original){
        setOriginal(original);
    }

    public void commit(){
        original.setFilasTablero(FilasTablero.get());
        original.setColumnasTablero(ColumnasTablero.get());

        original.setTurnosVidaIniciales(TurnosVidaIniciales.get());
        original.setProbReproIndividuo(ProbReproIndividuo.get());
        original.setProbClonIndividuo(ProbClonIndividuo.get());
        original.setProbMejoraToNormal(ProbMejoraToNormal.get());
        original.setProbMejoraToAvanzado(ProbMejoraToAvanzado.get());

        original.setProbAparRecurso(ProbAparRecurso.get());
        original.setTurnosInicialesRecurso(TurnosInicialesRecurso.get());
        original.setProbAparAgua(ProbAparAgua.get());
        original.setProbAparComida(ProbAparComida.get());
        original.setProbAparMontaña(ProbAparMontaña.get());
        original.setProbAparTesoro(ProbAparTesoro.get());
        original.setProbAparBiblioteca(ProbAparBiblioteca.get());
        original.setProbAparPozo(ProbAparPozo.get());
        original.setIncrementoTurnosAgua(IncrementoTurnosAgua.get());
        original.setIncrementoTurnosComida(IncrementoTurnosComida.get());
        original.setIncrementoTurnosMontaña(IncrementoTurnosMontaña.get());
        original.setIncrementoProbRepro(IncrementoProbRepro.get());
        original.setIncrementoProbClon(IncrementoProbClon.get());
    }

    public void rollback(Tab tabActual){
        try {
            if (tabActual == null) {
                rollbackIndividuos();
                rollbackRecursos();
                rollbackTablero();
            } else {
                switch (tabActual.getText()) {
                    case "Individuos":
                        rollbackIndividuos();
                        break;
                    case "Recursos":
                        rollbackRecursos();
                        break;
                    case "Tablero":
                        rollbackTablero();
                        break;
                    default:
                        throw new reinicioPestañaInesperadaException();
                }
            }
        } catch (reinicioPestañaInesperadaException e) {
            log.error("Se han tratado de reiniciar los valores de una pestaña inesperada");
        }
    }

    private void rollbackIndividuos () {
        TurnosVidaIniciales.set(original.getTurnosVidaIniciales());
        ProbReproIndividuo.set(original.getProbReproIndividuo());
        ProbClonIndividuo.set(original.getProbClonIndividuo());
        ProbMejoraToNormal.set(original.getProbMejoraToNormal());
        ProbMejoraToAvanzado.set(original.getProbMejoraToAvanzado());
    }

    private void rollbackRecursos () {
        ProbAparRecurso.set(original.getProbAparRecurso());
        TurnosInicialesRecurso.set(original.getTurnosInicialesRecurso());
        ProbAparAgua.set(original.getProbAparAgua());
        ProbAparComida.set(original.getProbAparComida());
        ProbAparMontaña.set(original.getProbAparMontaña());
        ProbAparTesoro.set(original.getProbAparTesoro());
        ProbAparBiblioteca.set(original.getProbAparBiblioteca());
        ProbAparPozo.set(original.getProbAparPozo());
        IncrementoTurnosAgua.set(original.getIncrementoTurnosAgua());
        IncrementoTurnosComida.set(original.getIncrementoTurnosComida());
        IncrementoTurnosMontaña.set(original.getIncrementoTurnosMontaña());
        IncrementoProbRepro.set(original.getIncrementoProbRepro());
        IncrementoProbClon.set(original.getIncrementoProbClon());
    }

    private void rollbackTablero () {
        FilasTablero.set(original.getFilasTablero());
        ColumnasTablero.set(original.getColumnasTablero());
    }

    public configuracionDataModel getOriginal(){
        return original;
    }

    public void setOriginal(configuracionDataModel original){
        this.original = original;
        rollback(null); //Se inicializan los properties.
    }

    public ObjectProperty<Integer> TurnosVidaInicialesProperty() {
        return TurnosVidaIniciales;
    }

    public Property<Number> ProbReproIndividuoProperty() {
        return ProbReproIndividuo;
    }

    public Property<Number> ProbClonIndividuoProperty() {
        return ProbClonIndividuo;
    }

    public Property<Number> ProbMejoraToNormalProperty() { return ProbMejoraToNormal; }

    public Property<Number> ProbMejoraToAvanzadoProperty() { return ProbMejoraToAvanzado; }

    public Property<Number> ProbAparRecursoProperty() {
        return ProbAparRecurso;
    }

    public Property<Number> ProbAparAguaProperty() {
        return ProbAparAgua;
    }

    public Property<Number> ProbAparComidaProperty() {
        return ProbAparComida;
    }

    public Property<Number> ProbAparMontañaProperty() {
        return ProbAparMontaña;
    }

    public Property<Number> ProbAparTesoroProperty() {
        return ProbAparTesoro;
    }

    public Property<Number> ProbAparBibliotecaProperty() {
        return ProbAparBiblioteca;
    }

    public Property<Number> ProbAparPozoProperty() {
        return ProbAparPozo;
    }

    public ObjectProperty<Integer> TurnosInicialesRecursoProperty() {
        return TurnosInicialesRecurso;
    }

    public ObjectProperty<Integer> IncrementoTurnosAguaProperty() {
        return IncrementoTurnosAgua;
    }

    public ObjectProperty<Integer> IncrementoTurnosComidaProperty() {
        return IncrementoTurnosComida;
    }

    public ObjectProperty<Integer> IncrementoTurnosMontañaProperty() {
        return IncrementoTurnosMontaña;
    }

    public Property<Number> IncrementoProbReproProperty() {
        return IncrementoProbRepro;
    }

    public Property<Number> IncrementoProbClonProperty() {
        return IncrementoProbClon;
    }

    public ObjectProperty<Integer> FilasTableroProperty() {
        return FilasTablero;
    }

    public ObjectProperty<Integer> ColumnasTableroProperty() {
        return ColumnasTablero;
    }
}