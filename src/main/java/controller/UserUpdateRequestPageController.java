package controller;

import dto.RequestDto;
import dto.ResidenceDto;
import dto.UserDto;
import exception.InvalidDocumentException;
import exception.InvalidRequestMakingException;
import exception.InvalidUserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mapper.UserMapper;
import service.RequestService;
import service.UserService;
import utils.ApplicationUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserUpdateRequestPageController implements Initializable {

    @FXML
    private TextField documentTypeTextField;

    @FXML
    private ChoiceBox<String> residenceBox;

    @FXML
    private TextArea textArea;

    private UserDto user;

    private UserService userService = new UserService();

    private RequestService requestService = new RequestService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = UserLoginPageController.loggedUser;
        List<ResidenceDto> residenceDtoList = userService.getUsersResidencies(UserMapper.dtoToEntity(user));

        for(ResidenceDto r: residenceDtoList){
            residenceBox.getItems().add(r.toString());
        }

        residenceBox.setValue(UserRequestsPageController.selectedRequest.getResidence());
        documentTypeTextField.setText(UserRequestsPageController.selectedRequest.getDocumentType());
        textArea.setText(UserRequestsPageController.selectedRequest.getDocumentContent());
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        goToUserRequestsPage(event);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        try {
            RequestDto requestDto = requestService.getRequestById(UserRequestsPageController.selectedRequest.getId());
            requestService.updateRequestByUser(requestDto, textArea.getText(), residenceBox.getValue());
            goToUserRequestsPage(event);
            ApplicationUtils.displayMessage("The request has been updated!","Request Updated","Assets/ok.png");
        } catch (InvalidDocumentException | InvalidUserException | InvalidRequestMakingException e) {
            ApplicationUtils.displayMessage(e.getMessage(),"Error","Assets/no.jpg");
        }
    }

    private void goToUserRequestsPage(ActionEvent event) {

        try {
            URL url = new File("src/main/java/gui/userMainPage.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            UserMainPageController userMainPageController = loader.getController();

            URL requestPageURL = new File("src/main/java/gui/userRequestsPage.fxml").toURI().toURL();
            FXMLLoader loader1 = new FXMLLoader(requestPageURL);
            Parent rootRequest = loader1.load();
            UserRequestsPageController userRequestsPageController = loader1.getController();

            userRequestsPageController.populateList();
            userMainPageController.getUserMainPane().getChildren().setAll(rootRequest.getChildrenUnmodifiable());

            Scene mainMenuScene= new Scene(root, 700,600);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
