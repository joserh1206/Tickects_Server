package Controllers;

//import Code.conector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VentanaIngresoServerController extends VentanaServerController{

    @FXML
    private JFXPasswordField pwfContraseniaAdmin;


    @FXML
    private JFXButton btnIngresarServer;

    /**
     * Valida los datos para que solo el administrador pueda conectarse
     * @param event
     * @throws IOException
     */
    @FXML
    void IngresarServer(ActionEvent event) throws IOException {
        if(Objects.equals(pwfContraseniaAdmin.getText(), "admin")) {
            Parent VentanaLogin_Parent = FXMLLoader.load(getClass().getResource("../FXMLs/VentanaServer.fxml"));
            Scene VentanaLogin_scene = new Scene(VentanaLogin_Parent);
            Stage App_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            App_Stage.close();
            App_Stage.setScene(VentanaLogin_scene);
            App_Stage.setTitle("Server");
            App_Stage.setResizable(false);
            App_Stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseña incorrecta");
            alert.setHeaderText("La contraseña ingresada es incorrecta");
            alert.setContentText("Asegurese de haber ingresado la contraseña correcta e intente de nuevo");
            alert.showAndWait();
        }
    }

}
