package Controllers;

import Code.conector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaIngresoServerController {

    @FXML
    private JFXPasswordField pwfContraseniaAdmin;

    @FXML
    private JFXButton btnIngresarServer;

    @FXML
    void IngresarServer(ActionEvent event) throws IOException {
        Parent VentanaLogin_Parent = FXMLLoader.load(getClass().getResource("../FXMLs/VentanaServer.fxml"));
        Scene VentanaLogin_scene = new Scene(VentanaLogin_Parent);
        Stage App_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        App_Stage.close();
        App_Stage.setScene(VentanaLogin_scene);
        App_Stage.setTitle("Server");
        App_Stage.setResizable(false);
        App_Stage.show();
    }

}
