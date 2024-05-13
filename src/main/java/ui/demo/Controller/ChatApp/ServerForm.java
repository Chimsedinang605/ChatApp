package ui.demo.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.demo.DAO.ObjectDAO;
import ui.demo.Model.Login;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    @FXML
    private Button btnchat;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<Login, Long> colUserid;

    @FXML
    private TableColumn<Login, String> colUsername;

    @FXML
    private TableColumn<Login, String> colgender;

    @FXML
    private TableColumn<Login, String> colpassword;

    @FXML
    private TextField gender;

    @FXML
    private TextField password;

    @FXML
    private TableView<Login> tableuser;

    @FXML
    private TextField username;

    @FXML
    private VBox uservbox;

    @FXML
    private AnchorPane showtable;

    public ObservableList<Login> listUser = null;
    ObjectDAO<Login> obj  = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showlist();
    }

    @FXML
    void switchForm(ActionEvent event) {

    }

    public void showlist( ) {
        colUserid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
//      truy cap ds user
        obj = new ObjectDAO<>();
        List list = obj.getAll();
        listUser = FXCollections.observableList(list);
        tableuser.setItems(listUser);
    }

    public void addUserClear() {
        username.setText(" ");
        password.setText(" ");
        gender.setText(" ");
    }

    @FXML
    void AddNew(MouseEvent event) {
        // Mở cửa sổ thêm người dùng mới
        openAddUserWindow();
    }

    private void openAddUserWindow() {
        // Code để mở cửa sổ thêm người dùng mới
        // Sau khi người dùng thêm thành công và nhấp vào nút "Save"
        // Thêm người dùng mới vào cơ sở dữ liệu và danh sách hiển thị
        // Ví dụ:
        Stage stage = new Stage();
        // Load FXML file cho cửa sổ thêm người dùng mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

        // Sau khi cửa sổ thêm người dùng đóng lại, kiểm tra liệu người dùng đã thêm thành công hay không
        if (isUserAddedSuccessfully) {
            // Thêm người dùng mới vào cơ sở dữ liệu và danh sách hiển thị
            String name = username.getText();
            String pass = password.getText();
            String gen = gender.getText();
            Login login = new Login(name, pass, gen);
            obj.saveUser(login);
            listUser.add(login);

            // Mở cửa sổ chat giữa client và server
            openChatWindow(login);
        }
    }

    private void openChatWindow(Login login) {
        // Code để mở cửa sổ chat giữa client và server
        // Ví dụ:
        Stage chatStage = new Stage();
        // Truyền thông tin đăng nhập vào cửa sổ chat
        FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
        Parent chatRoot = chatLoader.load();
        // Truyền thông tin đăng nhập vào Controller của cửa sổ chat
        ChatWindowController chatController = chatLoader.getController();
        chatController.initLogin(login);
        Scene chatScene = new Scene(chatRoot);
        chatStage.setScene(chatScene);
        chatStage.show();
    }


    @FXML
    void Delete(MouseEvent event) {
        obj = new ObjectDAO<>();
        String name = username.getText();
        String pass = password.getText();
        String gen = gender.getText();

        try {
//            tìm tên
            Login login = obj.getByUsername(name);
            if (login != null) {
                if (login.getPassword().equals(pass) && login.getGender().equals(gen)) {
                    obj.Delete(login);
//                    xoá thành công.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã xóa người dùng: " + name);
                    alert.showAndWait();
//                    update user
//                    showlist();

                    tableuser.getItems().remove(login);
                    showlist();
//                    xóa dữ liệu từ các trường nhập
                    addUserClear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy người dùng: " + name);
                    alert.showAndWait();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpDate(MouseEvent event) {
        obj = new ObjectDAO<>();
        String name = username.getText();
        String pass = password.getText();
        String gen = gender.getText();

        try {
//            find obj
            Login login = obj.getByUsername(name);
            if (login != null) {
//                update obj
                login.setPassword(pass);
                login.setGender(gen);

//                update in db
                obj.UpDate(login);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Đã cập nhật thông tin cho: " + name);
                alert.showAndWait();

                showlist();

//                xoa data tu cac truong nhap data
                addUserClear();
            } else  {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy người dùng: " + name);
                alert.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
