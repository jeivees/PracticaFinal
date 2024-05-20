module PracticaFinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires com.google.gson;

    opens gui.mvc.javafx.practicafinal to javafx.fxml, com.google.gson;
    opens es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada to com.google.gson;
    opens es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola to com.google.gson;
    opens es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple to com.google.gson;
    opens es.uah.matcomp.mp.simulaciondevida.elementos.individuos to com.google.gson;
    opens es.uah.matcomp.mp.simulaciondevida.elementos.entorno to com.google.gson;
    exports gui.mvc.javafx.practicafinal;
}

