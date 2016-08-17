package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    public static Engine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        engine = new Engine();
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("Main menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        //SynonymFinder synonymFinder = new SynonymFinder();
    }

    public static void close() {
        primaryStage.close(); // used for closing the application
    }

    /* Used to run the window for text modifying */
    public static void runTextModifyingWindow() {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new TextModifyingWindow().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        close();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void runAddingSynonyms() {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new AddSynonymsWindow().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        close();
    }
}
