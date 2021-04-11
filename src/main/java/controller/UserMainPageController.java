package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMainPageController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane userMainPane;

    private UserDto loggedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUser = UserLoginPageController.loggedUser;
        nameLabel.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
    }

    @FXML
    void myProfileLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/userProfilePage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void myRequestsLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/userRequestsPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logoutLabelOnAction(MouseEvent mouseEvent) {
        try{
            URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene mainMenuScene= new Scene(root, 593,400);
            Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exitButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public AnchorPane getUserMainPane() {
        return userMainPane;
    }

    public void setUserMainPane(AnchorPane userMainPane) {
        this.userMainPane = userMainPane;
    }

}
