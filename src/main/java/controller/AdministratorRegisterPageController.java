package controller;

import entity.Administrator;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mapper.AdministratorMapper;
import service.AdministratorService;
import utils.ApplicationUtils;
import validator.AdministratorValidator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AdministratorRegisterPageController {

    @FXML
    private Button exitButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailAddressTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private Label messageLabel;

    @FXML
    void registerButtonOnAction(ActionEvent event) {
        boolean isPasswordValid = AdministratorValidator.arePasswordsMatching(passwordTextFiled.getText(), confirmPasswordTextField.getText());

        if(isPasswordValid){
            Administrator administrator = new Administrator(firstNameTextField.getText(), lastNameTextField.getText(), emailAddressTextField.getText(), phoneNumberTextField.getText(), usernameTextField.getText(), passwordTextFiled.getText());
            try{
                AdministratorService administratorService = new AdministratorService();
                administratorService.addAdministrator(AdministratorMapper.entityToDto(administrator));

                URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);

                Scene adminLoginScene= new Scene(root, 593,400);
                Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                primaryStage.setScene(adminLoginScene);

            } catch (InvalidFirstNameException | InvalidLastNameException | InvalidPhoneNumberException | InvalidEmailException | InvalidUsernameException | InvalidPasswordException e) {
                ApplicationUtils.displayMessage(e.getMessage(),"Error","Assets/no.jpg");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ApplicationUtils.displayMessage(CustomExceptionMessages.PASSWORDS_NOT_MATCHING_MESSAGE,"Error","Assets/no.jpg");
            passwordTextFiled.setText("");
            confirmPasswordTextField.setText("");
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        try{
            URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene adminLoginScene= new Scene(root, 593,400);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(adminLoginScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
