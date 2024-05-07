package gui.mvc.javafx.practicafinal;

import excepciones.reinicioPestañaInesperadaException;
import javafx.beans.property.*;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class configuracionDataModelProperties {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    //Modelo de datos original
    protected configuracionDataModel original;

    private IntegerProperty TurnosVidaIniciales = new SimpleIntegerProperty();
    private IntegerProperty ProbReproIndividuo = new SimpleIntegerProperty();
    private IntegerProperty ProbClonIndividuo = new SimpleIntegerProperty();
    private IntegerProperty ProbAparAgua = new SimpleIntegerProperty();
    private IntegerProperty ProbAparComida = new SimpleIntegerProperty();
    private IntegerProperty ProbAparMontaña = new SimpleIntegerProperty();
    private IntegerProperty ProbAparTesoro = new SimpleIntegerProperty();
    private IntegerProperty ProbAparBiblioteca = new SimpleIntegerProperty();
    private IntegerProperty ProbAparPozo = new SimpleIntegerProperty();
    private IntegerProperty IncrementoTurnosAgua = new SimpleIntegerProperty();
    private IntegerProperty IncrementoTurnosComida = new SimpleIntegerProperty();
    private IntegerProperty IncrementoTurnosMontaña = new SimpleIntegerProperty();
    private IntegerProperty IncrementoProbRepro = new SimpleIntegerProperty();
    private IntegerProperty IncrementoProbClon = new SimpleIntegerProperty();
    private IntegerProperty FilasTablero = new SimpleIntegerProperty();
    private IntegerProperty ColumnasTablero = new SimpleIntegerProperty();


    public configuracionDataModelProperties(configuracionDataModel original){
        setOriginal(original);
    }

    public void commit(){
        original.setTurnosVidaIniciales(TurnosVidaIniciales.get());
        original.setProbReproIndividuo(ProbReproIndividuo.get());
        original.setProbClonIndividuo(ProbClonIndividuo.get());
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
    }

    private void rollbackRecursos () {
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

    public IntegerProperty TurnosVidaInicialesProperty() {
        return TurnosVidaIniciales;
    }

    public Property<Number> ProbReproIndividuoProperty() {
        return ProbReproIndividuo;
    }

    public Property<Number> ProbClonIndividuoProperty() {
        return ProbClonIndividuo;
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

    public IntegerProperty IncrementoTurnosAguaProperty() {
        return IncrementoTurnosAgua;
    }

    public IntegerProperty IncrementoTurnosComidaProperty() {
        return IncrementoTurnosComida;
    }

    public IntegerProperty IncrementoTurnosMontañaProperty() {
        return IncrementoTurnosMontaña;
    }

    public Property<Number> IncrementoProbReproProperty() {
        return IncrementoProbRepro;
    }

    public Property<Number> IncrementoProbClonProperty() {
        return IncrementoProbClon;
    }
    public IntegerProperty FilasTableroProperty() {
        return FilasTablero;
    }

    public IntegerProperty ColumnasTableroProperty() {
        return ColumnasTablero;
    }

}