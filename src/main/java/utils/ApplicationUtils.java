package utils;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class ApplicationUtils {

    public static void displayMessage(String message,String title, String path) {
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle(title);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.setGraphic(imageView);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }



}
