package upo20052959.ristorante;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MyResturantController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}