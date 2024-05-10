package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class configuracionDataModel {
    // listas de elementos
    private ListaEnlazada<individuo> individuos = new ListaEnlazada<>();
    private ListaEnlazada<recurso> recursos = new ListaEnlazada<>();

    // datos generales
    private Boolean isPausado = false;

    private IntegerProperty turnoProperty = new SimpleIntegerProperty();

    // datos tablero
    private int IndividuosMaximosPorCelda = 3;
    private int RecursosMaximosPorCelda = 3;
    private int FilasTablero;
    private int ColumnasTablero;

    // datos individuo
    private int TurnosVidaIniciales;
    private int ProbReproIndividuo;
    private int ProbClonIndividuo;
    private int probMejoraToBasico;
    private int getProbMejoraToAvanzado;

    // datos recurso
    private int TurnosInicialesRecurso;
    private int ProbAparAgua;
    private int ProbAparComida;
    private int ProbAparMontaña;
    private int ProbAparTesoro;
    private int ProbAparBiblioteca;
    private int ProbAparPozo;
    private int IncrementoTurnosAgua;
    private int IncrementoTurnosComida;
    private int IncrementoTurnosMontaña;
    private int IncrementoProbRepro;
    private int IncrementoProbClon;



    public configuracionDataModel(int turnosVidaIniciales, int probReproIndividuo, int probClonIndividuo,
                                  int turnosInicialesRecurso, int probAparAgua, int probAparComida, int probAparMontaña, int probAparTesoro, int probAparBiblioteca, int probAparPozo, int incrementoTurnosAgua, int incrementoTurnosComida, int incrementoTurnosMontaña, int incrementoProbRepro, int incrementoProbClon,
                                  int filasTablero, int columnasTablero, int mejoraToBasico, int mejoraToAvanzado, int Turno) {
        TurnosVidaIniciales = turnosVidaIniciales;
        ProbReproIndividuo = probReproIndividuo;
        ProbClonIndividuo = probClonIndividuo;
        ProbAparAgua = probAparAgua;
        ProbAparComida = probAparComida;
        ProbAparMontaña = probAparMontaña;
        ProbAparTesoro = probAparTesoro;
        ProbAparBiblioteca = probAparBiblioteca;
        ProbAparPozo = probAparPozo;
        TurnosInicialesRecurso = turnosInicialesRecurso;
        IncrementoTurnosAgua = incrementoTurnosAgua;
        IncrementoTurnosComida = incrementoTurnosComida;
        IncrementoTurnosMontaña = incrementoTurnosMontaña;
        IncrementoProbRepro = incrementoProbRepro;
        IncrementoProbClon = incrementoProbClon;
        FilasTablero = filasTablero;
        ColumnasTablero = columnasTablero;
        probMejoraToBasico = mejoraToBasico;
        getProbMejoraToAvanzado = mejoraToAvanzado;
        turnoProperty.set(Turno);
        turnoProperty.set(0);
    }


    public int getTurnosVidaIniciales() {
        return TurnosVidaIniciales;
    }

    public void setTurnosVidaIniciales(int turnosVidaIniciales) {
        TurnosVidaIniciales = turnosVidaIniciales;
    }

    public int getProbReproIndividuo() {
        return ProbReproIndividuo;
    }

    public void setProbReproIndividuo(int probReproIndividuo) {
        ProbReproIndividuo = probReproIndividuo;
    }

    public int getProbClonIndividuo() {
        return ProbClonIndividuo;
    }

    public void setProbClonIndividuo(int probClonIndividuo) {
        ProbClonIndividuo = probClonIndividuo;
    }

    public int getProbAparAgua() {
        return ProbAparAgua;
    }

    public void setProbAparAgua(int probAparAgua) {
        ProbAparAgua = probAparAgua;
    }

    public int getProbAparComida() {
        return ProbAparComida;
    }

    public void setProbAparComida(int probAparComida) {
        ProbAparComida = probAparComida;
    }

    public int getProbAparMontaña() {
        return ProbAparMontaña;
    }

    public void setProbAparMontaña(int probAparMontaña) {
        ProbAparMontaña = probAparMontaña;
    }

    public int getProbAparTesoro() {
        return ProbAparTesoro;
    }

    public void setProbAparTesoro(int probAparTesoro) {
        ProbAparTesoro = probAparTesoro;
    }

    public int getProbAparBiblioteca() {
        return ProbAparBiblioteca;
    }

    public void setProbAparBiblioteca(int probAparBiblioteca) {
        ProbAparBiblioteca = probAparBiblioteca;
    }

    public int getProbAparPozo() {
        return ProbAparPozo;
    }

    public void setProbAparPozo(int probAparPozo) {
        ProbAparPozo = probAparPozo;
    }

    public int getIncrementoTurnosAgua() {
        return IncrementoTurnosAgua;
    }

    public void setIncrementoTurnosAgua(int incrementoTurnosAgua) {
        IncrementoTurnosAgua = incrementoTurnosAgua;
    }

    public int getIncrementoTurnosComida() {
        return IncrementoTurnosComida;
    }

    public void setIncrementoTurnosComida(int incrementoTurnosComida) {
        IncrementoTurnosComida = incrementoTurnosComida;
    }

    public int getIncrementoTurnosMontaña() {
        return IncrementoTurnosMontaña;
    }

    public void setIncrementoTurnosMontaña(int incrementoTurnosMontaña) {
        IncrementoTurnosMontaña = incrementoTurnosMontaña;
    }

    public int getIncrementoProbRepro() {
        return IncrementoProbRepro;
    }

    public void setIncrementoProbRepro(int incrementoProbRepro) {
        IncrementoProbRepro = incrementoProbRepro;
    }

    public int getIncrementoProbClon() {
        return IncrementoProbClon;
    }

    public void setIncrementoProbClon(int incrementoProbClon) {
        IncrementoProbClon = incrementoProbClon;
    }

    public int getFilasTablero() {
        return FilasTablero;
    }

    public void setFilasTablero(int filasTablero) {
        FilasTablero = filasTablero;
    }

    public int getColumnasTablero() {
        return ColumnasTablero;
    }

    public void setColumnasTablero(int columnasTablero) {
        ColumnasTablero = columnasTablero;
    }

    public int getIndividuosMaximosPorCelda() {
        return IndividuosMaximosPorCelda;
    }

    public void setIndividuosMaximosPorCelda(int individuosMaximosPorCelda) {
        IndividuosMaximosPorCelda = individuosMaximosPorCelda;
    }

    public int getRecursosMaximosPorCelda() {
        return RecursosMaximosPorCelda;
    }

    public void setRecursosMaximosPorCelda(int recursosMaximosPorCelda) {
        RecursosMaximosPorCelda = recursosMaximosPorCelda;
    }

    public int getProbMejoraToBasico() {
        return probMejoraToBasico;
    }

    public void setProbMejoraToBasico(int probMejoraToBasico) {
        this.probMejoraToBasico = probMejoraToBasico;
    }

    public int getProbMejoraToAvanzado() {
        return getProbMejoraToAvanzado;
    }

    public void setProbMejoraToAvanzado(int getProbMejoraToAvanzado) {
        this.getProbMejoraToAvanzado = getProbMejoraToAvanzado;
    }

    public Boolean isPausado() {
        return isPausado;
    }

    public void setPausado(Boolean pausado) {
        isPausado = pausado;
    }
    public ListaEnlazada<individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ListaEnlazada<individuo> individuos) {
        this.individuos = individuos;
    }

    public ListaEnlazada<recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(ListaEnlazada<recurso> recursos) {
        this.recursos = recursos;
    }

    public IntegerProperty getTurnoProperty() {
        return turnoProperty;
    }

    public int getTurno() {
        return turnoProperty.get();
    }

    public void setTurno(int turno) {
        turnoProperty.set(turno);
    }

    public int getTurnosInicialesRecurso() {
        return TurnosInicialesRecurso;
    }

    public void setTurnosInicialesRecurso(int turnosInicialesRecurso) {
        TurnosInicialesRecurso = turnosInicialesRecurso;
    }
}
