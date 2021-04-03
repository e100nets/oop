package santech;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import santech.controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {

    public  static Config conf;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        Parent root = FXMLLoader.load(getClass().getResource("view/gui.fxml"));
        primaryStage.setTitle("Сантехника");

        int vert = 720;
        int hor  = 1024;
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, hor, vert));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
