package controller;

import dto.RequestDto;
import dto.RequestTableDto;
import dto.UserDto;
import entity.DocumentType;
import entity.Request;
import exception.CustomExceptionMessages;
import exception.InvalidDocumentException;
import exception.InvalidRequestMakingException;
import exception.InvalidUserException;
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
import mapper.DocumentTypeMapper;
import mapper.RequestMapper;
import mapper.UserMapper;
import service.DocumentTypeService;
import service.RequestService;
import service.UserService;
import utils.ApplicationUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AdministratorRequestsPageController implements Initializable {

    @FXML
    private TableView<RequestTableDto> requestTable;

    @FXML
    private ListView<RequestTableDto> requestListView;

    @FXML
    private ChoiceBox<String> documentTypeBox;

    @FXML
    private ChoiceBox<String> userBox;

    protected static ObservableList<RequestTableDto> data = FXCollections.observableArrayList();

    protected static ObservableList<RequestTableDto> listViewData = FXCollections.observableArrayList();

    private RequestService requestService = new RequestService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateList();
        setChoiceBoxes();
    }

    private void setChoiceBoxes() {
        DocumentTypeService documentTypeService = new DocumentTypeService();
        List<String> documentList = documentTypeService.getDocumentTypes();
        for (String s : documentList) {
            documentTypeBox.getItems().add(s);
        }

        UserService userService = new UserService();
        List<UserDto> userDtoList = userService.getAllUsers();
        for (UserDto u : userDtoList) {
            userBox.getItems().add(u.getFirstName() + " " + u.getLastName());
        }
    }

    public void populateList() {
        data.clear();
        List<RequestDto> requestDtoList = requestService.getAllRequests();
        List<RequestTableDto> requestTableDtoList = new ArrayList<>();

        for (RequestDto r : requestDtoList) {
            RequestTableDto requestTableDto = RequestMapper.dtoToTableDto(r);
            requestTableDtoList.add(requestTableDto);
        }

        data = FXCollections.observableArrayList(requestTableDtoList);
        populateTableView(data);
    }

    public void populateTableView(ObservableList<RequestTableDto> dataList) {
        requestTable.setItems(dataList);
    }

    public void populateListView(ObservableList<RequestTableDto> dataList) {
        requestListView.setItems(dataList);
    }

    @FXML
    void approveRequestButtonOnAction(ActionEvent event) {
        if (requestTable.getSelectionModel().getSelectedItem() == null) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_TABLE_ELEMENT_SELECTED, "Error", "Assets/no.jpg");
        } else {
            RequestTableDto requestTableDto = requestTable.getSelectionModel().getSelectedItem();
            try {
                requestService.approveRequest(RequestMapper.tableDtoToDto(requestTableDto));
                populateList();
            } catch (InvalidDocumentException | InvalidUserException e) {
                ApplicationUtils.displayMessage(e.getMessage(), "Error", "Assets/no.jpg");
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
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        try {
            URL url = new File("src/main/java/gui/administratorMainPage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene userMainScene = new Scene(root, 700, 600);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(userMainScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {
        String documentTypeBoxValue = documentTypeBox.getValue();
        String userBoxValue = userBox.getValue();

        List<RequestTableDto> requestTableDtoList = requestService.searchRequestByUserAndDocumentType(userBoxValue, documentTypeBoxValue);
        makeTableDtoListFromDtoList(requestTableDtoList);
    }

    @FXML
    void sortTypeAscButtonOnAction(ActionEvent event) {

        List<RequestDto> requestDtoList = requestService.getAllRequests();
        requestDtoList = requestService.sortRequestDtoAscending(requestDtoList);
        makeTableDtoListFromDtoList(requestService.createTableDtoListFromDtoList(requestDtoList));
    }

    @FXML
    void sortTypeDescButtonOnAction(ActionEvent event) {
        List<RequestDto> requestDtoList = requestService.getAllRequests();
        requestDtoList = requestService.sortRequestDtoDescending(requestDtoList);
        makeTableDtoListFromDtoList(requestService.createTableDtoListFromDtoList(requestDtoList));
    }

    @FXML
    void sortByUsersAscButtonOnAction(ActionEvent event) {
        List<RequestDto> requestDtoList = requestService.getAllRequests();
        List<RequestTableDto> requestTableDtoList = requestService.createTableDtoListFromDtoList(requestDtoList);

        requestTableDtoList = requestService.sortRequestTableDtoAscending(requestTableDtoList);
        listViewData = FXCollections.observableArrayList(requestTableDtoList);
        populateListView(listViewData);

    }

    @FXML
    void sortByUsersDescButtonOnAction(ActionEvent event) {
        List<RequestDto> requestDtoList = requestService.getAllRequests();
        List<RequestTableDto> requestTableDtoList = requestService.createTableDtoListFromDtoList(requestDtoList);

        requestTableDtoList = requestService.sortRequestTableDtoDescending(requestTableDtoList);
        listViewData = FXCollections.observableArrayList(requestTableDtoList);
        populateListView(listViewData);
    }

    private void makeTableDtoListFromDtoList(List<RequestTableDto> requestTableDtos) {
        if(requestTableDtos != null){
            listViewData = FXCollections.observableArrayList(requestTableDtos);
            populateListView(listViewData);
        }
    }

    @FXML
    void sortByApprovalDateButtonOnAction(ActionEvent event) {
        List<RequestDto> requestDtoList = requestService.getRequestsByApproval(true);
        requestDtoList = requestService.sortRequestDtoDescending(requestDtoList);
        makeTableDtoListFromDtoList(requestService.createTableDtoListFromDtoList(requestDtoList));
    }

    @FXML
    void searchNotApprovedButtonOnAction(ActionEvent event) {
        List<RequestDto> requestDtoList = requestService.getRequestsByApproval(false);
        requestDtoList = requestService.sortRequestDtoAscending(requestDtoList);
        makeTableDtoListFromDtoList(requestService.createTableDtoListFromDtoList(requestDtoList));
    }
}
