package hr.kaba.olb.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = (new FXMLLoader()).load(getClass().getResourceAsStream("/fxml/dummy.fxml"));


        primaryStage.setScene(new Scene(root, 300, 500));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}