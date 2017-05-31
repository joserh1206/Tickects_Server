package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaServerController {

    @FXML
    private Label lblActivityLog;

    @FXML
    private JFXTreeTableView<?> ttvEmpleados;

    @FXML
    private JFXTreeTableView<?> ttvTicketsServer;

    @FXML
    private TextField txfEmpleadoEstadisticas;

    @FXML
    private JFXButton btnConsultarEstadisticas;

    @FXML
    private JFXButton btnDesconectarServer;

    @FXML
    void ConsultarEstadisticasServer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/VentanaEstadisticas.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Estadisticas");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void DesconectarServer(ActionEvent event) {

    }

}
