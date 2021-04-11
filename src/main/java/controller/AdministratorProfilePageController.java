package controller;

import dto.AdministratorDto;
import entity.Administrator;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mapper.AdministratorMapper;
import service.AdministratorService;
import utils.ApplicationUtils;
import validator.AdministratorValidator;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorProfilePageController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField emailAddressTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private AdministratorDto administrator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administrator = AdministratorLoginPageController.loggedAdministrator;
        initializeFields();
    }

    private void initializeFields() {
        usernameTextField.setText(administrator.getUsername());
        passwordField.setText(administrator.getPassword());
        confirmPasswordField.setText(administrator.getPassword());
        firstNameTextField.setText(administrator.getFirstName());
        lastNameTextField.setText(administrator.getLastName());
        emailAddressTextField.setText(administrator.getEmailAddress());
        phoneNumberTextField.setText(administrator.getPhoneNumber());
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        boolean isPasswordValid = AdministratorValidator.arePasswordsMatching(passwordField.getText(), confirmPasswordField.getText());

        if(isPasswordValid){
            try {

                Administrator updatedAdministrator = updateAdministrator();
                AdministratorService administratorService = new AdministratorService();
                AdministratorLoginPageController.loggedAdministrator =  AdministratorMapper.entityToDto(updatedAdministrator);
                administratorService.updateAdministrator(AdministratorMapper.entityToDto(updatedAdministrator));

                goToAdministratorMainPage(event);
                ApplicationUtils.displayMessage("The changes have been saved!","Profile Updated","Assets/ok.png");
            } catch (InvalidFirstNameException | InvalidLastNameException | InvalidPhoneNumberException | InvalidEmailException | InvalidPasswordException e) {
                ApplicationUtils.displayMessage(e.getMessage(),"Error","Assets/no.jpg");
            }
        } else {
            ApplicationUtils.displayMessage(CustomExceptionMessages.PASSWORDS_NOT_MATCHING_MESSAGE,"Error","Assets/no.jpg");
            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }

    private Administrator updateAdministrator() {
        Administrator updatedAdministrator = new Administrator();
        updatedAdministrator.setId(administrator.getId());
        updatedAdministrator.setFirstName(firstNameTextField.getText());
        updatedAdministrator.setLastName(lastNameTextField.getText());
        updatedAdministrator.setUsername(administrator.getUsername());
        updatedAdministrator.setPassword(passwordField.getText());
        updatedAdministrator.setEmailAddress(emailAddressTextField.getText());
        updatedAdministrator.setPhoneNumber(phoneNumberTextField.getText());
        return updatedAdministrator;
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        goToAdministratorMainPage(event);
    }

    private void goToAdministratorMainPage(ActionEvent event) {
        try{
            URL url = new File("src/main/java/gui/administratorMainPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene userMainScene= new Scene(root, 700,600);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(userMainScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
