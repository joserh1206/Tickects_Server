package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    void ConsultarEstadisticasServer(ActionEvent event) {

    }

    @FXML
    void DesconectarServer(ActionEvent event) {

    }

}
