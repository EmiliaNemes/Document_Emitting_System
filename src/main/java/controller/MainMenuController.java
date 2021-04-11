package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

        @FXML
        private ImageView logoImage;

        @FXML
        private ImageView userLogoImage;

        @FXML
        private ImageView adminLogoImage;

        @FXML
        private Button exitButton;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle){
                File userFile = new File("Assets/user.jpg");
                Image customerImage = new Image(userFile.toURI().toString());
                userLogoImage.setImage(customerImage);

                File adminFile = new File("Assets/admin.png");
                Image adminImage = new Image(adminFile.toURI().toString());
                adminLogoImage.setImage(adminImage);

                File logoFile = new File("Assets/logo.PNG");
                Image mainLogoImage = new Image(logoFile.toURI().toString());
                logoImage.setImage(mainLogoImage);
        }

        @FXML
        void adminLoginButtonOnAction(ActionEvent event) {
                try{
                        URL url = new File("src/main/java/gui/administratorLoginPage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene adminLoginScene= new Scene(root, 600,400);
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        primaryStage.setScene(adminLoginScene);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        void adminRegisterButtonOnAction(ActionEvent event) {
                try{
                        URL url = new File("src/main/java/gui/administratorRegisterPage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene adminRegisterScene= new Scene(root, 618,522);
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        primaryStage.setScene(adminRegisterScene);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        void userLoginButtonOnAction(ActionEvent event) {
                try{
                        URL url = new File("src/main/java/gui/userLoginPage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene userLoginScene= new Scene(root, 600,450);
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        primaryStage.setScene(userLoginScene);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        void userRegisterButtonOnAction(ActionEvent event) {
                try{
                        URL url = new File("src/main/java/gui/userRegisterPage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);

                        Scene userRegisterScene= new Scene(root, 618,533);
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        primaryStage.setScene(userRegisterScene);
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
