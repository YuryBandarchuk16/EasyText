package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button exitButton, addSynonymsButton, modifyTheTextButton;

    @FXML
    private void exitApplication() {
        Main.close(); // close the window
    }

    @FXML
    private void openSynonymsWindow() {

    }

    @FXML
    private void openTextModifyingWindow() {
        Main.runTextModifyingWindow(); // open the window which is responsible for text modifying
    }

}
