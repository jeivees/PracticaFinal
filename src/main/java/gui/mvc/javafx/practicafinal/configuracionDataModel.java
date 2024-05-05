package gui.mvc.javafx.practicafinal;

public class configuracionDataModel {
    private int TurnosVidaIniciales;
    private int ProbReproIndividuo;
    private int ProbClonIndividuo;

    public configuracionDataModel(int turnosVidaIniciales, int probReproIndividuo, int probClonIndividuo) {
        TurnosVidaIniciales = turnosVidaIniciales;
        ProbReproIndividuo = probReproIndividuo;
        ProbClonIndividuo = probClonIndividuo;
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
}
