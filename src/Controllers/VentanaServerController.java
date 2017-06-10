package Controllers;

import Code.Main;
import Code.conector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import static Code.Main.*;

public class VentanaServerController{
    public static Funcionarios jose = new Funcionarios("José Luis Rodríguez", "Desconectado");
    public static Funcionarios randall = new Funcionarios("Randall Delgado", "Desconectado");
    public static Funcionarios profe = new Funcionarios("Erika Shumann", "Desconectado");

    @FXML
    private Label lblActivityLog;

    @FXML
    private JFXTreeTableView<Funcionarios> ttvEmpleados;

    @FXML
    private JFXTreeTableView<?> ttvTicketsServer;

    @FXML
    private TextField txfEmpleadoEstadisticas;

    @FXML
    private JFXButton btnConsultarEstadisticas;

    @FXML
    private JFXButton btnDesconectarServer;

    @FXML
    private JFXButton btnConectar;

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
    void Conectar(ActionEvent event) throws IOException {
        btnDesconectarServer.setDisable(false);
        IniciarTabla();
//            System.out.println("0");
        conector c = new conector("Thread");
        c.start();
//            System.out.println("1");
//            c.iniciar();
//            c.Ingreso();
//            lblActivityLog.setText(lblActivityLog.getText() + "\n " + user + "  se ha conectado!");
//            jose.estado.setValue("Conectado");
//            System.out.println("2");
        btnConectar.setDisable(true);

    }

    @FXML
    void DesconectarServer(ActionEvent event) throws IOException {
        if (Main.conectado = true) {
            btnDesconectarServer.setDisable(true);
            Main.conectado = false;
            entrada.close();
            salida.close();
            server.close();
            socket.close();
            btnConectar.setDisable(false);
            //jose.estado.setValue("Desconectado");
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server");
            alert.setHeaderText("Mensaje del servidor");
            alert.setContentText("El servidor ya se encuentra desactivado");
            alert.showAndWait();
        }
    }
    public void IniciarTabla() {
        JFXTreeTableColumn<Funcionarios, String> colNombre = new JFXTreeTableColumn<>("Funcionario");
        colNombre.setPrefWidth(150);
        colNombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Funcionarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Funcionarios, String> param) {
                return param.getValue().getValue().nombre;
            }
        });

        JFXTreeTableColumn<Funcionarios, String> colEstado = new JFXTreeTableColumn<>("Estado");
        colEstado.setPrefWidth(150);
        colEstado.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Funcionarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Funcionarios, String> param) {
                return param.getValue().getValue().estado;
            }
        });


        ObservableList<Funcionarios> funcionarios = FXCollections.observableArrayList();
        funcionarios.add(jose);
        funcionarios.add(randall);
        funcionarios.add(profe);

        final TreeItem<Funcionarios> root = new RecursiveTreeItem<Funcionarios>(funcionarios, RecursiveTreeObject::getChildren);
        ttvEmpleados.getColumns().setAll(colNombre, colEstado);
        ttvEmpleados.setRoot(root);
        ttvEmpleados.setShowRoot(false);
        ttvEmpleados.autosize();



    }

    public static class Funcionarios extends RecursiveTreeObject<Funcionarios>{

        public StringProperty nombre;
        public StringProperty estado;

        public Funcionarios (String nombre, String estado){
            this.nombre = new SimpleStringProperty(nombre);
            this.estado = new SimpleStringProperty(estado);
        }

    }

}