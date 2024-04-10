module es.uah.matcomp.mp.teoria.gui.mvc.javafx.simulaciondevida {
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

    opens es.uah.matcomp.mp.teoria.gui.mvc.javafx.simulaciondevida to javafx.fxml;
    exports es.uah.matcomp.mp.teoria.gui.mvc.javafx.simulaciondevida;
}