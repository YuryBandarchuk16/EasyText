package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("Main menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        /*Controller controller = new Controller();
        controller.exitButton = new Button("exitButton");
        int nClicks = 0;
        controller.exitButton.setOnAction(event -> {
            System.out.println("Clicked " + 0 + " times.");
        });
        if (controller.exitButton == null) {
            System.out.println("NULL!");
        } else {
            System.out.println("Everything is ok!");
        }*/
    }

    public static void close() {
        primaryStage.close();
    }

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}
