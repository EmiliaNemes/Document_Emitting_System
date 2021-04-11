package controller;

import dto.*;
import entity.DocumentType;
import exception.CustomExceptionMessages;
import exception.InvalidDocumentNameException;
import exception.InvalidEmissionDateException;
import exception.InvalidTypeException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mapper.DocumentTypeMapper;
import service.DocumentTypeService;
import service.RequestService;
import utils.ApplicationUtils;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorDocumentPageController implements Initializable {

    @FXML
    private TableView<DocumentTypeDto> documentsTable;

    @FXML
    private TextField documentNameTextField;

    public static ObservableList<DocumentTypeDto> data = FXCollections.observableArrayList();

    private DocumentTypeService documentTypeService = new DocumentTypeService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        documentTypeService.populateList();
        populateTableView(data);
    }


    public void populateTableView(ObservableList<DocumentTypeDto> dataList) {
        documentsTable.setItems(dataList);
    }

    @FXML
    void addDocumentButtonOnAction(ActionEvent event) {
        if (documentNameTextField.getText().equals("")) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_DOCUMENT_NAME_WRITTEN, "Error", "Assets/no.jpg");
        } else {
            DocumentTypeDto documentTypeDto = new DocumentTypeDto(documentNameTextField.getText());
            try {
                documentTypeService.addDocumentType(documentTypeDto);
                documentNameTextField.setText("");
                data.add(documentTypeDto);
                populateTableView(data);
                ApplicationUtils.displayMessage("A new document type has been added!", "Document Added", "Assets/ok.png");
            } catch (InvalidEmissionDateException e) {
                ApplicationUtils.displayMessage(e.getMessage(), "Error", "Assets/no.jpg");
            } catch (InvalidTypeException e) {
                ApplicationUtils.displayMessage(e.getMessage(), "Error", "Assets/no.jpg");
            } catch (InvalidDocumentNameException e) {
                ApplicationUtils.displayMessage(e.getMessage(), "Error", "Assets/no.jpg");
            }
        }
    }

    @FXML
    void deleteDocumentButtonOnAction(ActionEvent event) {
        if (documentsTable.getSelectionModel().getSelectedItem() == null) {
            ApplicationUtils.displayMessage(CustomExceptionMessages.NO_TABLE_ELEMENT_SELECTED, "Error", "Assets/no.jpg");
        } else {
            DocumentTypeDto documentTypeDto = documentsTable.getSelectionModel().getSelectedItem();
            if(documentTypeService.deleteApprovedDocuments(documentTypeDto)){
                populateTableView(data);
                ApplicationUtils.displayMessage("The document has been deleted!", "Document Deleted", "Assets/ok.png");
            } else {
                populateTableView(data);
            }
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
}
