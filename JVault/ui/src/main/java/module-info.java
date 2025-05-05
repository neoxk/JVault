module si.um.feri.jvault {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens si.um.feri.jvault to javafx.fxml;
    opens si.um.feri.jvault.controllers to javafx.fxml;
    opens si.um.feri.jvault.managers to javafx.base;
    exports si.um.feri.jvault;
    exports si.um.feri.jvault.controllers;
    exports si.um.feri.jvault.managers;
}