package ui.demo.Controller;

import ui.demo.Model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.demo.DAO.ObjectDAO;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    ObjectDAO<Login> obj = null;

    @FXML
    void Login(ActionEvent event) throws IOException {
//        enter user/password
        String u = username.getText();
        String p = password.getText();

        if( u.isEmpty() || p.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login Error");
            alert.setContentText("Vui lòng điền tên đăng nhập/mật kẩu của bạn!");
            alert.show();
        } else {
//            connect database and check
            obj = new ObjectDAO<>();
            Login user = obj.findUser(u,p);
            if( user == null ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Login Error");
                alert.setContentText("Tên đăng nhập/mật kẩu của bạn không hợp lệ!");
                alert.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/chat/ChatServer.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage  stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Server Form");
                stage.show();
//                close login stage
                Stage loginStage = (Stage) username.getScene().getWindow();
                loginStage.close();
            }
        }


    }

}
