package controller;

import dto.UserDto;
import dto.UsersTableDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mapper.UserMapper;
import service.UserService;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorViewUsersPageController implements Initializable {

    @FXML
    private TableView<UsersTableDto> usersTable;

    protected static ObservableList<UsersTableDto> data = FXCollections.observableArrayList();

    private UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateList();
    }

    public void populateList() {

        data.clear();
        List<UserDto> userDtoList = userService.getAllUsers();
        List<UsersTableDto> usersTableDtoList = userService.createUserTableDtoFromUserDto(userDtoList);
        data = FXCollections.observableArrayList(usersTableDtoList);
        populateTableView(data);
    }

    public void populateTableView(ObservableList<UsersTableDto> dataList) {
        usersTable.setItems(dataList);
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
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
