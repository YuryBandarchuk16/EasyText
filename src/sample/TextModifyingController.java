package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TextModifyingController {

    @FXML
    private Button updateTextButton, backButton;

    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    private void backButtonClicked() {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new Main().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        TextModifyingWindow.close();
    }

    @FXML
    private void updateTextButtonClicked() throws Exception {
        String[] lines = inputTextArea.getText().split("\\n");
        outputTextArea.clear();
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(Main.engine.update(line));
            stringBuilder.append("\n");
        }
        outputTextArea.setText(stringBuilder.toString());
    }

}
