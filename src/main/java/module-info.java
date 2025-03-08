module upo20052959.ristorante{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;


    opens upo20052959.ristorante to javafx.fxml;
    exports upo20052959.ristorante;
}