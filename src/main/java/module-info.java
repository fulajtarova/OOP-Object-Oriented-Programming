module com.example.triparchitect {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.triparchitect to javafx.fxml;
    exports com.example.triparchitect;
    opens com.example.triparchitect.users to javafx.fxml;
}