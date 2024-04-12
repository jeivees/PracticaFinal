package gui.mvc.javafx.practicafinal;

import es.uah.matcomp.mp.simulaciondevida.simuladorDeVida;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class menuConfiguracionController {
    @FXML
    private Label Titulo;

    @FXML
    protected void onNuevoButtonClick() {
        simuladorDeVida nuevoJuego = new simuladorDeVida();
        nuevoJuego.comenzar();
    }

    @FXML
    protected void onCargarButtonClick() {
        simuladorDeVida juegoGuardado = new simuladorDeVida();
        juegoGuardado.cargarJuego().comenzar();
    }
}