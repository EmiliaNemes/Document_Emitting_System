package start;

import dto.*;
import entity.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Application;
import mapper.*;
import service.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ApplicationStart extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        //hiding control buttons (minimize, maximize, close window)
        primaryStage.initStyle(StageStyle.UNDECORATED);


        primaryStage.setTitle("Sistemul de Emitere a Documentelor");
        primaryStage.setX(350);
        primaryStage.setY(50);
        primaryStage.setScene(new Scene(root, 593, 403));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
