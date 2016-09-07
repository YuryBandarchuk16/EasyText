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

    private int collisionResult = -1;

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

    private void createAlert(String textButton, String text) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setOnCloseRequest(e -> Platform.exit());
        Button b = new Button(textButton);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogStage.close();
            }
        });
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(new Text(text), b).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();
        return;
    }

    private void createSynonymsCollisionAlert(String wordOne, String wordTwo) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setOnCloseRequest(e -> Platform.exit());
        Button b1 = new Button("YES");
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.engine.removeSynonymForWord(wordOne);
                try {
                    collisionResult = Main.engine.addPairOfSynonyms(wordOne, wordTwo);
                    createAlert("OK", "This word and its synonym have been replaced!");
                } catch (FileNotFoundException e) {
                    collisionResult = -2;
                    e.printStackTrace();
                }
                dialogStage.close();

            }
        });
        String text = "";
        try {
            text = "The word " + wordOne + " has a synonym = " + Main.engine.getSynonym(wordOne) + "\n" + "Would you like to replace it?";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button b2 = new Button("NO");
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                collisionResult = -3;
                dialogStage.close();
            }
        });
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(new Text(text), b1, b2).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();
    }


    @FXML
    private void addButtonClicked() throws FileNotFoundException {
        String wordOne = firstWord.getText();
        String wordTwo = secondWord.getText();
        wordOne = Main.engine.toLower(wordOne);
        wordTwo = Main.engine.toLower(wordTwo);
        int minLength = Math.min(wordOne.length(), wordTwo.length());
        if (minLength == 0) {
            createAlert("OK", "Enter valid words!");
            return;
        }
        int code = Main.engine.addPairOfSynonyms(wordOne, wordTwo);
        if (code == 0) {
            createAlert("OK", "Pair of synonyms has been successfully added!");
        } else if (code == 1) {
            createAlert("OK", "Enter valid words!");
        } else {
            collisionResult = -1;
            createSynonymsCollisionAlert(wordOne, wordTwo);
        }
    }

}
