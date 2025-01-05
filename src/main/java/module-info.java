module org.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.demo2 to javafx.fxml;
    //exports org.example.demo2;
}