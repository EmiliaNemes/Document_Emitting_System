package controller;

import dto.RequestDto;
import dto.RequestTableDto;
import entity.Request;
import exception.CustomExceptionMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mapper.RequestMapper;
import mapper.UserMapper;
import service.RequestService;
import service.UserService;
import utils.ApplicationUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserRequestsPageController implements Initializable {

    @FXML
    public TableColumn<RequestTableDto, String> documentTypeColumn;
    @FXML
    public TableColumn<RequestTableDto, String> documentContentColumn;
    @FXML
    public TableColumn<RequestTableDto, String> residenceColumn;
    @FXML
    public TableColumn<RequestTableDto, String> approvedColumn;
    @FXML
    public TableColumn<RequestTableDto, String> approvalDateColumn;
    @FXML
    private TableView<RequestTableDto> requestTable;

    protected static ObservableList<RequestTableDto> data = FXCollections.observableArrayList();

    protected static RequestTableDto selectedRequest;

    private UserService userService = new UserService();

    private RequestService requestService = new RequestService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateList();
    }

    public void populateList() {

        data.clear();
        List<RequestDto> requestList = userService.getUsersRequests(UserMapper.dtoToEntity(UserLoginPageController.loggedUser));
        List<RequestTableDto> requestTableDtoList = requestService.createTableDtoListFromDtoList(requestList);
        data = FXCollections.observableArrayList(requestTableDtoList);
        populateTableView(data);
    }

    public void populateTableView(ObservableList<RequestTableDto> dataList) {
        requestTable.setItems(dataList);
    }

    @FXML
    void addRequestButtonOnAction(ActionEvent event) {
        try {
            URL url = new File("src/main/java/gui/userMainPage.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            UserMainPageController userMainPageController = loader.getController();

            URL addRequestPageURL = new File("src/main/java/gui/userAddRequestPage.fxml").toURI().toURL();
            Parent rootAddRequest = FXMLLoader.load(addRequestPageURL);
            userMainPageController.getUserMainPane().getChildren().setAll(rootAddRequest.getChildrenUnmodifiable());

            Scene mainMenuScene= new Scene(root, 700,600);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateRequestButtonOnAction(ActionEvent event) {
        if (requestTable.getSelectionModel().getSelectedItem() == null) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_TABLE_ELEMENT_SELECTED, "Error", "Assets/no.jpg");
        } else {
            selectedRequest = requestTable.getSelectionModel().getSelectedItem();
            if (RequestMapper.tableDtoToDto(selectedRequest).isApproved()) {
                ApplicationUtils.displayMessage("This request cannot be updated! It is already approved!", "Update", "Assets/no.jpg");
            } else {
                try {
                    URL url = new File("src/main/java/gui/userMainPage.fxml").toURI().toURL();
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = loader.load();
                    UserMainPageController userMainPageController = loader.getController();

                    URL addRequestPageURL = new File("src/main/java/gui/userUpdateRequestPage.fxml").toURI().toURL();
                    Parent rootUpdateRequest = FXMLLoader.load(addRequestPageURL);
                    userMainPageController.getUserMainPane().getChildren().setAll(rootUpdateRequest.getChildrenUnmodifiable());

                    Scene mainMenuScene = new Scene(root, 700, 600);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(mainMenuScene);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void deleteRequestButtonOnAction(ActionEvent event) {
        if (requestTable.getSelectionModel().getSelectedItem() == null) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_TABLE_ELEMENT_SELECTED, "Error", "Assets/no.jpg");
        } else {
            RequestTableDto requestTableDto = requestTable.getSelectionModel().getSelectedItem();
            requestService.deleteRequest(requestTableDto.getId());
            populateList();
            ApplicationUtils.displayMessage("The request has been deleted!", "Request Delete", "Assets/ok.png");
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
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

    public ObservableList<RequestTableDto> getData() {
        return data;
    }

    public void setData(ObservableList<RequestTableDto> data) {
        this.data = data;
    }
}
