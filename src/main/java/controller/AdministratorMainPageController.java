package controller;

import dto.AdministratorDto;
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
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorMainPageController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane userMainPane;

    private AdministratorDto loggedAdministrator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedAdministrator = AdministratorLoginPageController.loggedAdministrator;
        nameLabel.setText(loggedAdministrator.getFirstName() + " " + loggedAdministrator.getLastName());
    }

    @FXML
    void myProfileLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/administratorProfilePage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void usersLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/administratorViewUsersPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void documentsLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/administratorDocumentPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void requestsLabelOnAction(MouseEvent event) {
        try {
            URL url = new File("src/main/java/gui/administratorRequestsPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            userMainPane.getChildren().setAll(root.getChildrenUnmodifiable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutLabelOnAction(MouseEvent event) {
        try{
            URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene mainMenuScene= new Scene(root, 593,400);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void exitButtonOnAction(ActionEvent event) {
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
