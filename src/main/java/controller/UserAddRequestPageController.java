package controller;

import dto.*;
import exception.CustomExceptionMessages;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mapper.UserMapper;
import service.DocumentTypeService;
import service.RequestService;
import service.UserService;
import utils.ApplicationUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserAddRequestPageController implements Initializable {

    @FXML
    private ChoiceBox<String> documentTypeBox;

    @FXML
    private ChoiceBox<String> residenceBox;

    @FXML
    private TextArea textArea;

    private UserDto user;

    private RequestDto requestDto;

    private DocumentTypeService documentTypeService = new DocumentTypeService();

    private UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = UserLoginPageController.loggedUser;

        List<String> documentList = documentTypeService.getDocumentTypes();
        List<ResidenceDto> residenceDtoList = userService.getUsersResidencies(UserMapper.dtoToEntity(user));

        for(String s: documentList){
            documentTypeBox.getItems().add(s);
        }

        for(ResidenceDto r: residenceDtoList){
            residenceBox.getItems().add(r.toString());
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        goToUserRequestsPage(event);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) throws IOException {

        if(documentTypeBox.getValue() == null && residenceBox.getValue() == null){
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_DOCUMENT_TYPE_AND_RESIDENCE_CHOSEN,"Error","Assets/no.jpg");
        } else {
            if(documentTypeBox.getValue() != null && residenceBox.getValue() == null){
                ApplicationUtils.displayMessage(CustomExceptionMessages.NO_RESIDENCE_CHOSEN,"Error","Assets/no.jpg");
            } else {
                if(documentTypeBox.getValue() == null && residenceBox.getValue() != null){
                    ApplicationUtils.displayMessage(CustomExceptionMessages.NO_DOCUMENT_TYPE_CHOSEN,"Error","Assets/no.jpg");
                } else {
                    RequestService requestService = new RequestService();
                    String selectedType = documentTypeBox.getValue();
                    String selectedResidence = residenceBox.getValue();
                    String content = textArea.getText();

                    try {
                        requestDto = requestService.makeRequest(user, selectedType, content, selectedResidence);
                        goToUserRequestsPage(event);
                        ApplicationUtils.displayMessage(CustomExceptionMessages.NEW_REQUEST_ADDED,"New Request","Assets/ok.png");
                    } catch (InvalidDocumentException | InvalidUserException | InvalidRequestMakingException e) {
                        ApplicationUtils.displayMessage(e.getMessage(),"Error","Assets/no.jpg");
                    }
                }
            }
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
