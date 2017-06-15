package Controllers;

import Code.*;
//import Code.conector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static Code.Main.*;
//import static Code.conector.user;

public class VentanaServerController{

    public static Funcionarios jose = new Funcionarios("José Luis Rodríguez", "Desconectado");
    public static Funcionarios randall = new Funcionarios("Randall Delgado", "Desconectado");
    public static Funcionarios profe = new Funcionarios("Erika Shumann", "Desconectado");

    public static String log = "";

    int puerto = 9000;

    @FXML
    private Label lblActivityLog;

    @FXML
    private JFXTreeTableView<Funcionarios> ttvEmpleados;

    @FXML
    private JFXTreeTableView<Ticket> ttvTicketsServer;

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
        MostrarTickets();
//            System.out.println("0");
        Server server = new Server(puerto);

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
            lblActivityLog.setText(lblActivityLog.getText() + "\n " + log + "  se ha conectado!");
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

    public void MostrarTickets() {
        JFXTreeTableColumn<Ticket, String> colFecha = new JFXTreeTableColumn<>("Fecha y Hora de Recepción");
        colFecha.setPrefWidth(150);
        colFecha.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().strFechaYHoraRecepcion;
            }
        });

        JFXTreeTableColumn<Ticket, String> colIDCliente = new JFXTreeTableColumn<>("ID de Cliente");
        colIDCliente.setPrefWidth(150);
        colIDCliente.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().idCliente;
            }
        });

        JFXTreeTableColumn<Ticket, String> colAsunto = new JFXTreeTableColumn<>("Asunto");
        colAsunto.setPrefWidth(150);
        colAsunto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().asunto;
            }
        });

        ObservableList<Ticket> tickets = FXCollections.observableArrayList();

        EditorExcel x = new EditorExcel();
        ArrayList<Ticket> ticketsArchivo = x.cargar(new File("Tickets.xls"));

        for (int i = 0; i < ticketsArchivo.size(); i++){
            tickets.add(ticketsArchivo.get(i));
        }

        final TreeItem<Ticket> root = new RecursiveTreeItem<Ticket>(tickets, RecursiveTreeObject::getChildren);
        ttvTicketsServer.getColumns().setAll(colFecha, colIDCliente, colAsunto);
        ttvTicketsServer.setRoot(root);
        ttvTicketsServer.setShowRoot(false);
        ttvTicketsServer.autosize();

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