package controller;

import dto.RequestDto;
import dto.ResidenceDto;
import dto.UserDto;
import entity.Residence;
import entity.User;
import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mapper.ResidenceMapper;
import mapper.UserMapper;
import service.RequestService;
import service.ResidenceService;
import service.UserService;
import utils.ApplicationUtils;
import validator.ResidenceValidator;
import validator.UserValidator;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfilePageController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailAddressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TableView<ResidenceDto> residenceTable;

    private UserDto user;

    ObservableList<ResidenceDto> data = FXCollections.observableArrayList();

    private ResidenceService residenceService = new ResidenceService();
    private RequestService requestService = new RequestService();
    private UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        user = UserLoginPageController.loggedUser;
        initializeFields();
        initializeTable();

    }

    public void initializeFields(){
        usernameTextField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        confirmPasswordField.setText(user.getPassword());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        emailAddressTextField.setText(user.getEmailAddress());
        phoneNumberTextField.setText(user.getPhoneNumber());
    }

    public void initializeTable(){
        UserService userService = new UserService();
        List<ResidenceDto> residenceList = userService.getUsersResidencies(UserMapper.dtoToEntity(UserLoginPageController.loggedUser));
        data = FXCollections.observableArrayList(residenceList);
        populateTableView(data);
    }

    private void populateTableView(ObservableList<ResidenceDto> dataList) {
        residenceTable.setItems(dataList);
    }

    @FXML
    void addResidenceButtonOnAction(ActionEvent event) {
        if(cityTextField.getText().equals("") && streetTextField.getText().equals("") && numberTextField.getText().equals("")){
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_RESIDENCE_ENTERED, "Error", "Assets/no.jpg");
        }else {
            boolean isResidenceValid = ResidenceValidator.isResidenceValid(cityTextField.getText(), streetTextField.getText(), numberTextField.getText());

            if (isResidenceValid) {
                try {
                    Residence residence = new Residence(cityTextField.getText(), streetTextField.getText(), Integer.valueOf(numberTextField.getText()), UserMapper.dtoToEntity(user));
                    residenceService.addResidence(ResidenceMapper.entityToDto(residence));
                    cityTextField.setText("");
                    streetTextField.setText("");
                    numberTextField.setText("");
                    data.add(ResidenceMapper.entityToDto(residence));
                    populateTableView(data);
                    ApplicationUtils.displayMessage("A new residence has been added!", "New Residence", "Assets/ok.png");
                } catch (InvalidStreetException | InvalidNumberException | InvalidCityException e) {
                    ApplicationUtils.displayMessage(e.getMessage(), "Error", "Assets/no.jpg");
                }
            } else {
                ApplicationUtils.displayMessage(CustomExceptionMessages.INVALID_RESIDENCE_MESSAGE, "Error", "Assets/no.jpg");
            }
        }
    }

    @FXML
    void deleteResidenceButtonOnAction(ActionEvent event) {
        if (residenceTable.getSelectionModel().getSelectedItem() == null) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_TABLE_ELEMENT_SELECTED, "Error", "Assets/no.jpg");
        } else {
            ResidenceDto residenceDto = residenceTable.getSelectionModel().getSelectedItem();
            List<RequestDto> requestDtoList = userService.getUsersRequests(UserMapper.dtoToEntity(UserLoginPageController.loggedUser));

            boolean allApproved = requestService.areAllRequestsApproved(requestDtoList, residenceDto);
            if (allApproved) {
                requestService.eliminateRequests(requestDtoList, residenceDto);
                residenceService.removeResidence(residenceDto.getId());
                data.remove(residenceDto);
                populateTableView(data);
                ApplicationUtils.displayMessage("The residence has been deleted! The requests made for this residence has also been deleted!", "Residence Delete", "Assets/ok.png");
            } else {
                ApplicationUtils.displayMessage(CustomExceptionMessages.INVALID_RESIDENCE_DELETE_MESSAGE, "Error", "Assets/no.jpg");
            }
        }
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        boolean isPasswordValid = UserValidator.arePasswordsMatching(passwordField.getText(), confirmPasswordField.getText());

        if(isPasswordValid){
                try {
                    User updatedUser = updateUser();
                    UserLoginPageController.loggedUser =  UserMapper.entityToDto(updatedUser);
                    userService.updateUser(UserMapper.entityToDto(updatedUser));
                    goToUserMainPage(event);
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

    private User updateUser(){
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setFirstName(firstNameTextField.getText());
        updatedUser.setLastName(lastNameTextField.getText());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(passwordField.getText());
        updatedUser.setEmailAddress(emailAddressTextField.getText());
        updatedUser.setPhoneNumber(phoneNumberTextField.getText());

        return updatedUser;
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        goToUserMainPage(event);
    }

    private void goToUserMainPage(ActionEvent event){
        try{
            URL url = new File("src/main/java/gui/userMainPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene userMainScene= new Scene(root, 700,600);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(userMainScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
