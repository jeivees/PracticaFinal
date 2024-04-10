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

    opens gui.mvc.javafx to javafx.fxml;
    exports src.main.resources.gui.mvc.javafx;
}