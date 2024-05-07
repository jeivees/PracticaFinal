package gui.mvc.javafx.practicafinal;

public class configuracionDataModel {
    private int TurnosVidaIniciales;
    private int ProbReproIndividuo;
    private int ProbClonIndividuo;
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


    public configuracionDataModel(int turnosVidaIniciales, int probReproIndividuo, int probClonIndividuo, int probAparAgua, int probAparComida, int probAparMontaña, int probAparTesoro, int probAparBiblioteca, int probAparPozo, int incrementoTurnosAgua, int incrementoTurnosComida, int incrementoTurnosMontaña, int incrementoProbRepro, int incrementoProbClon) {
        TurnosVidaIniciales = turnosVidaIniciales;
        ProbReproIndividuo = probReproIndividuo;
        ProbClonIndividuo = probClonIndividuo;
        ProbAparAgua = probAparAgua;
        ProbAparComida = probAparComida;
        ProbAparMontaña = probAparMontaña;
        ProbAparTesoro = probAparTesoro;
        ProbAparBiblioteca = probAparBiblioteca;
        ProbAparPozo = probAparPozo;
        IncrementoTurnosAgua = incrementoTurnosAgua;
        IncrementoTurnosComida = incrementoTurnosComida;
        IncrementoTurnosMontaña = incrementoTurnosMontaña;
        IncrementoProbRepro = incrementoProbRepro;
        IncrementoProbClon = incrementoProbClon;
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
}
