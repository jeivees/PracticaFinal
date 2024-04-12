package gui.mvc.javafx.practicafinal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import es.uah.matcomp.mp.simulaciondevida.*;

public class menuPrincipalController {
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