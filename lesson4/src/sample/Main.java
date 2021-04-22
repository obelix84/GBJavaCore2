package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //попробуем так
        Screen screen = Screen.getPrimary();
        Rectangle2D display = screen.getBounds();
        double height = display.getHeight();
        double width = display.getWidth();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Simple chat");
        primaryStage.setScene(new Scene(root, (int) width / 3, (int) height / 3));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
