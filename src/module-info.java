module notetakingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ci.pigier to javafx.fxml;
    opens ci.pigier.controllers.ui to javafx.fxml;
    opens ci.pigier.model to javafx.base;

    exports ci.pigier;
    exports ci.pigier.controllers.ui;
}
