package gui.mvc.javafx.practicafinal;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.gsonAdapterRecurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.gsonAdapterIndividuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class DataModel {
    private static final Logger log = LogManager.getLogger();

    // listas de elementos
    @Expose
    private ListaEnlazada<individuo> individuos = new ListaEnlazada<>();
    @Expose
    private ListaEnlazada<recurso> recursos = new ListaEnlazada<>();
    @Expose
    private ListaEnlazada<individuo> HistorialIndividuos = new ListaEnlazada<>();
    @Expose
    private ListaEnlazada<recurso> HistorialRecursos = new ListaEnlazada<>();

    // datos generales
    @Expose
    private boolean isPausado = false;
    @Expose
    private boolean isGuardado = true;
    @Expose
    private String nombreArchivo;

    @Expose
    private int turnoActual;

    // datos tablero
    @Expose
    private int IndividuosMaximosPorCelda = 3;
    @Expose
    private int RecursosMaximosPorCelda = 3;
    @Expose
    private int FilasTablero;
    @Expose
    private int ColumnasTablero;

    // datos individuo
    @Expose
    private int TurnosVidaIniciales;
    @Expose
    private int ProbReproIndividuo;
    @Expose
    private int ProbClonIndividuo;
    @Expose
    private int ProbMejoraToNormal;
    @Expose
    private int ProbMejoraToAvanzado;

    // datos recurso
    @Expose
    private int ProbAparRecurso;
    @Expose
    private int TurnosInicialesRecurso;
    @Expose
    private int ProbAparAgua;
    @Expose
    private int ProbAparComida;
    @Expose
    private int ProbAparMontaña;
    @Expose
    private int ProbAparTesoro;
    @Expose
    private int ProbAparBiblioteca;
    @Expose
    private int ProbAparPozo;
    @Expose
    private int IncrementoTurnosAgua;
    @Expose
    private int IncrementoTurnosComida;
    @Expose
    private int IncrementoTurnosMontaña;
    @Expose
    private int IncrementoProbRepro;
    @Expose
    private int IncrementoProbClon;


    public DataModel(int turnosVidaIniciales, int probReproIndividuo, int probClonIndividuo, int probMejoraTonormal, int probMejoraToAvanzado,
                     int probAparRecurso, int turnosInicialesRecurso, int probAparAgua, int probAparComida, int probAparMontaña, int probAparTesoro, int probAparBiblioteca, int probAparPozo, int incrementoTurnosAgua, int incrementoTurnosComida, int incrementoTurnosMontaña, int incrementoProbRepro, int incrementoProbClon,
                     int filasTablero, int columnasTablero, int turno) {
        TurnosVidaIniciales = turnosVidaIniciales;
        ProbReproIndividuo = probReproIndividuo;
        ProbClonIndividuo = probClonIndividuo;
        ProbMejoraToNormal = probMejoraTonormal;
        ProbMejoraToAvanzado = probMejoraToAvanzado;

        ProbAparRecurso = probAparRecurso;
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
        turnoActual = turno;
    }

    public void guardar (String nombreArchivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(individuo.class, new gsonAdapterIndividuo())
                .registerTypeAdapter(recurso.class, new gsonAdapterRecurso())
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setPrettyPrinting()
                .create();
        try (FileWriter writer = new FileWriter(STR."archivosDePartida/\{nombreArchivo}.json")) {
            this.setGuardado(true);
            gson.toJson(this, writer);
        } catch (IOException e) {
            log.error("La ruta para guardar el archivo no existe");
        }
    }

    public static DataModel cargar (String nombreArchivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(individuo.class, new gsonAdapterIndividuo())
                .registerTypeAdapter(recurso.class, new gsonAdapterRecurso())
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setPrettyPrinting()
                .create();
        try (FileReader reader = new FileReader(STR."archivosDePartida/\{nombreArchivo}")) {
            return gson.fromJson(reader, DataModel.class);
        } catch (IOException e) {
            log.error("La ruta para cargar el archivo no existe");
            return null;
        }
    }

    public int getTurnosVidaIniciales() {
        return TurnosVidaIniciales;
    }

    public void setTurnosVidaIniciales(int turnosVidaIniciales) {
        TurnosVidaIniciales = turnosVidaIniciales;
        setGuardado(false);
    }

    public int getProbReproIndividuo() {
        return ProbReproIndividuo;
    }

    public void setProbReproIndividuo(int probReproIndividuo) {
        ProbReproIndividuo = probReproIndividuo;
        setGuardado(false);
    }

    public int getProbClonIndividuo() {
        return ProbClonIndividuo;
    }

    public void setProbClonIndividuo(int probClonIndividuo) {
        ProbClonIndividuo = probClonIndividuo;
        setGuardado(false);
    }

    public int getProbAparAgua() {
        return ProbAparAgua;
    }

    public void setProbAparAgua(int probAparAgua) {
        ProbAparAgua = probAparAgua;
        setGuardado(false);
    }

    public int getProbAparComida() {
        return ProbAparComida;
    }

    public void setProbAparComida(int probAparComida) {
        ProbAparComida = probAparComida;
        setGuardado(false);
    }

    public int getProbAparMontaña() {
        return ProbAparMontaña;
    }

    public void setProbAparMontaña(int probAparMontaña) {
        ProbAparMontaña = probAparMontaña;
        setGuardado(false);
    }

    public int getProbAparTesoro() {
        return ProbAparTesoro;
    }

    public void setProbAparTesoro(int probAparTesoro) {
        ProbAparTesoro = probAparTesoro;
        setGuardado(false);
    }

    public int getProbAparBiblioteca() {
        return ProbAparBiblioteca;
    }

    public void setProbAparBiblioteca(int probAparBiblioteca) {
        ProbAparBiblioteca = probAparBiblioteca;
        setGuardado(false);
    }

    public int getProbAparPozo() {
        return ProbAparPozo;
    }

    public void setProbAparPozo(int probAparPozo) {
        ProbAparPozo = probAparPozo;
        setGuardado(false);
    }

    public int getIncrementoTurnosAgua() {
        return IncrementoTurnosAgua;
    }

    public void setIncrementoTurnosAgua(int incrementoTurnosAgua) {
        IncrementoTurnosAgua = incrementoTurnosAgua;
        setGuardado(false);
    }

    public int getIncrementoTurnosComida() {
        return IncrementoTurnosComida;
    }

    public void setIncrementoTurnosComida(int incrementoTurnosComida) {
        IncrementoTurnosComida = incrementoTurnosComida;
        setGuardado(false);
    }

    public int getIncrementoTurnosMontaña() {
        return IncrementoTurnosMontaña;
    }

    public void setIncrementoTurnosMontaña(int incrementoTurnosMontaña) {
        IncrementoTurnosMontaña = incrementoTurnosMontaña;
        setGuardado(false);
    }

    public int getIncrementoProbRepro() {
        return IncrementoProbRepro;
    }

    public void setIncrementoProbRepro(int incrementoProbRepro) {
        IncrementoProbRepro = incrementoProbRepro;
        setGuardado(false);
    }

    public int getIncrementoProbClon() {
        return IncrementoProbClon;
    }

    public void setIncrementoProbClon(int incrementoProbClon) {
        IncrementoProbClon = incrementoProbClon;
        setGuardado(false);
    }

    public int getFilasTablero() {
        return FilasTablero;
    }

    public void setFilasTablero(int filasTablero) {
        FilasTablero = filasTablero;
        setGuardado(false);
    }

    public int getColumnasTablero() {
        return ColumnasTablero;
    }

    public void setColumnasTablero(int columnasTablero) {
        ColumnasTablero = columnasTablero;
        setGuardado(false);
    }

    public int getIndividuosMaximosPorCelda() {
        return IndividuosMaximosPorCelda;
    }

    public void setIndividuosMaximosPorCelda(int individuosMaximosPorCelda) {
        IndividuosMaximosPorCelda = individuosMaximosPorCelda;
        setGuardado(false);
    }

    public int getRecursosMaximosPorCelda() {
        return RecursosMaximosPorCelda;
    }

    public void setRecursosMaximosPorCelda(int recursosMaximosPorCelda) {
        RecursosMaximosPorCelda = recursosMaximosPorCelda;
        setGuardado(false);
    }

    public int getProbMejoraToNormal() {
        return ProbMejoraToNormal;
    }

    public void setProbMejoraToNormal(int ProbMejoraToNormal) {
        this.ProbMejoraToNormal = ProbMejoraToNormal;
        setGuardado(false);
    }

    public int getProbMejoraToAvanzado() {
        return ProbMejoraToAvanzado;
    }

    public void setProbMejoraToAvanzado(int ProbMejoraToAvanzado) {
        this.ProbMejoraToAvanzado = ProbMejoraToAvanzado;
        setGuardado(false);
    }

    public Boolean isPausado() {
        return isPausado;
    }

    public void setPausado(Boolean pausado) {
        isPausado = pausado;
        setGuardado(false);
    }
    public ListaEnlazada<individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ListaEnlazada<individuo> individuos) {
        this.individuos = individuos;
        setGuardado(false);
    }

    public ListaEnlazada<recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(ListaEnlazada<recurso> recursos) {
        this.recursos = recursos;
        setGuardado(false);
    }

    public int getTurno() {
        return turnoActual;
    }

    public void setTurno(int turno) {
        this.turnoActual = turno;
        setGuardado(false);
    }

    public int getTurnosInicialesRecurso() {
        return TurnosInicialesRecurso;
    }

    public void setTurnosInicialesRecurso(int turnosInicialesRecurso) {
        TurnosInicialesRecurso = turnosInicialesRecurso;
        setGuardado(false);
    }

    public ListaEnlazada<individuo> getHistorialIndividuos() {
        return HistorialIndividuos;
    }

    public void setHistorialIndividuos(ListaEnlazada<individuo> historialIndividuos) {
        HistorialIndividuos = historialIndividuos;
        setGuardado(false);
    }

    public ListaEnlazada<recurso> getHistorialRecursos() {
        return HistorialRecursos;
    }

    public void setHistorialRecursos(ListaEnlazada<recurso> historialRecursos) {
        HistorialRecursos = historialRecursos;
        setGuardado(false);
    }
    public int getProbAparRecurso() {
        return ProbAparRecurso;
    }

    public void setProbAparRecurso(int probAparRecurso) {
        ProbAparRecurso = probAparRecurso;
        setGuardado(false);
    }

    public boolean isGuardado() {
        return isGuardado;
    }

    public void setGuardado(boolean guardado) {
        isGuardado = guardado;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
