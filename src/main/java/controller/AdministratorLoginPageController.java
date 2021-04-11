package controller;

import dto.AdministratorDto;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AdministratorService;
import utils.ApplicationUtils;

import javax.persistence.NoResultException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AdministratorLoginPageController {

        @FXML
        private Button exitButton;

        @FXML
        private TextField usernameTextField;

        @FXML
        private PasswordField passwordTextField;

        protected static AdministratorDto loggedAdministrator;

        @FXML
        void loginButtonOnAction(ActionEvent event) {
                AdministratorService administratorService = new AdministratorService();

                try {
                        loggedAdministrator = administratorService.getAdministratorByUsernameAndPassword(usernameTextField.getText(), passwordTextField.getText());
                        URL url = new File("src/main/java/gui/administratorMainPage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene userMainPageScene= new Scene(root, 700,600);
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        primaryStage.setScene(userMainPageScene);
                } catch(NoResultException | MalformedURLException e){
                        ApplicationUtils.displayMessage(CustomExceptionMessages.INVALID_USERNAME_OR_PASSWORD_MESSAGE,"Error","Assets/no.jpg");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void cancelButtonOnAction(ActionEvent event) {
                try {
                        URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene adminLoginScene = new Scene(root, 593, 400);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(adminLoginScene);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void exitButtonOnAction(ActionEvent event) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
        }

}

