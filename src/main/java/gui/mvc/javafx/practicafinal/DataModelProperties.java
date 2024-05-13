package gui.mvc.javafx.practicafinal;

import excepciones.reinicioPestañaInesperadaException;
import javafx.beans.property.*;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataModelProperties {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);

    //Modelo de datos original
    protected DataModel model;


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
    private ObjectProperty<Integer> ProbAparRecurso = new SimpleIntegerProperty().asObject();
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


    public DataModelProperties(DataModel model){
        setModelInitialize(model);
    }

    public void commit(){
        model.setFilasTablero(FilasTablero.get());
        model.setColumnasTablero(ColumnasTablero.get());

        model.setTurnosVidaIniciales(TurnosVidaIniciales.get());
        model.setProbReproIndividuo(ProbReproIndividuo.get());
        model.setProbClonIndividuo(ProbClonIndividuo.get());
        model.setProbMejoraToNormal(ProbMejoraToNormal.get());
        model.setProbMejoraToAvanzado(ProbMejoraToAvanzado.get());

        model.setProbAparRecurso(ProbAparRecurso.get());
        model.setTurnosInicialesRecurso(TurnosInicialesRecurso.get());
        model.setProbAparAgua(ProbAparAgua.get());
        model.setProbAparComida(ProbAparComida.get());
        model.setProbAparMontaña(ProbAparMontaña.get());
        model.setProbAparTesoro(ProbAparTesoro.get());
        model.setProbAparBiblioteca(ProbAparBiblioteca.get());
        model.setProbAparPozo(ProbAparPozo.get());
        model.setIncrementoTurnosAgua(IncrementoTurnosAgua.get());
        model.setIncrementoTurnosComida(IncrementoTurnosComida.get());
        model.setIncrementoTurnosMontaña(IncrementoTurnosMontaña.get());
        model.setIncrementoProbRepro(IncrementoProbRepro.get());
        model.setIncrementoProbClon(IncrementoProbClon.get());
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
        TurnosVidaIniciales.set(model.getTurnosVidaIniciales());
        ProbReproIndividuo.set(model.getProbReproIndividuo());
        ProbClonIndividuo.set(model.getProbClonIndividuo());
        ProbMejoraToNormal.set(model.getProbMejoraToNormal());
        ProbMejoraToAvanzado.set(model.getProbMejoraToAvanzado());
    }

    private void rollbackRecursos () {
        ProbAparRecurso.set(model.getProbAparRecurso());
        TurnosInicialesRecurso.set(model.getTurnosInicialesRecurso());
        ProbAparAgua.set(model.getProbAparAgua());
        ProbAparComida.set(model.getProbAparComida());
        ProbAparMontaña.set(model.getProbAparMontaña());
        ProbAparTesoro.set(model.getProbAparTesoro());
        ProbAparBiblioteca.set(model.getProbAparBiblioteca());
        ProbAparPozo.set(model.getProbAparPozo());
        IncrementoTurnosAgua.set(model.getIncrementoTurnosAgua());
        IncrementoTurnosComida.set(model.getIncrementoTurnosComida());
        IncrementoTurnosMontaña.set(model.getIncrementoTurnosMontaña());
        IncrementoProbRepro.set(model.getIncrementoProbRepro());
        IncrementoProbClon.set(model.getIncrementoProbClon());
    }

    private void rollbackTablero () {
        FilasTablero.set(model.getFilasTablero());
        ColumnasTablero.set(model.getColumnasTablero());
    }

    public DataModel getModel(){
        return model;
    }

    public void setModel (DataModel model) {
        this.model = model;
    }

    public void setModelInitialize(DataModel model){
        this.model = model;
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

    public ObjectProperty<Integer> ProbAparRecursoProperty() {
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