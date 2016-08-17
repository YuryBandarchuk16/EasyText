package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;

public class AddSynonymsWindowController {

    @FXML
    private TextField firstWord, secondWord;

    @FXML
    private Button addButton, backButton;

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
        AddSynonymsWindow.close();
    }

    @FXML
    private void addButtonClicked() throws FileNotFoundException {
        String wordOne = firstWord.getText();
        String wordTwo = secondWord.getText();
        int minLength = Math.min(wordOne.length(), wordTwo.length());
        if (minLength == 0) {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setOnCloseRequest(e -> Platform.exit());
            Button b = new Button("OK");
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                }
            });
            dialogStage.setScene(new Scene(VBoxBuilder.create().
                    children(new Text("Enter valid words"), b).
                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
            dialogStage.show();
            return;
        }
        Main.engine.addPairOfSynonyms(wordOne, wordTwo);
    }

}