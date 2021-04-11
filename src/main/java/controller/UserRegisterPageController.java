package controller;

import entity.Residence;
import entity.User;
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
import mapper.ResidenceMapper;
import mapper.UserMapper;
import service.ResidenceService;
import service.UserService;
import utils.ApplicationUtils;
import validator.ResidenceValidator;
import validator.UserValidator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserRegisterPageController {

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
    private PasswordField passwordTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField numberTextField;

    private ResidenceService residenceService = new ResidenceService();

    private UserService userService = new UserService();

    @FXML
    void registerButtonOnAction(ActionEvent event) {
        boolean isPasswordValid = UserValidator.arePasswordsMatching(passwordTextField.getText(), confirmPasswordTextField.getText());
        boolean isResidenceValid = ResidenceValidator.isResidenceValid(cityTextField.getText(), streetTextField.getText(), numberTextField.getText());

        if(isPasswordValid){
            if(isResidenceValid) {
                User user = new User(firstNameTextField.getText(), lastNameTextField.getText(), emailAddressTextField.getText(), phoneNumberTextField.getText(), usernameTextField.getText(), passwordTextField.getText());
                try {
                    userService.addUser(UserMapper.entityToDto(user));
                    Residence residence = new Residence(cityTextField.getText(), streetTextField.getText(), Integer.valueOf(numberTextField.getText()), user);
                    residenceService.addResidence(ResidenceMapper.entityToDto(residence));

                    URL url = new File("src/main/java/gui/mainMenu.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene adminLoginScene= new Scene(root, 593,400);
                    Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    primaryStage.setScene(adminLoginScene);
                } catch (InvalidFirstNameException | InvalidLastNameException | InvalidPhoneNumberException | InvalidEmailException | InvalidUsernameException | InvalidPasswordException | InvalidStreetException | InvalidNumberException | InvalidCityException e) {
                    ApplicationUtils.displayMessage(e.getMessage(),"Error","Assets/no.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                ApplicationUtils.displayMessage(CustomExceptionMessages.INVALID_RESIDENCE_MESSAGE,"Error","Assets/no.jpg");
            }
        } else {
            ApplicationUtils.displayMessage(CustomExceptionMessages.PASSWORDS_NOT_MATCHING_MESSAGE,"Error","Assets/no.jpg");
            passwordTextField.setText("");
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
